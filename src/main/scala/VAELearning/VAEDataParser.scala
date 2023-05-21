package VAELearning

import utilities.Pair

import java.io.{File, FileNotFoundException}
import java.util.{InputMismatchException, Scanner}
import scala.collection.JavaConverters.asJava


class VAEDataParser[DomType]() {

    def parse(path: String, objConstructor: Array[String] => DomType): java.util.List[DomType] = {

        var data : List[DomType] = List()

        try {

            val scanner = new Scanner(new File(path))

            scanner.next()

            while (scanner.hasNext()) {

                // Get nex row from scanner

                var str : String = scanner.next()

                // Split on "," and then on "-" if possible. Then flatten the array and filter out empty values.
                // Finally, convert to a list.

                var row : Array[String] = str
                    .split(",")
                    .map { c => c.trim }
                    .flatMap { c => c.split("-") }
                    .filter(_.nonEmpty)

                // Construct the domain object from the row.

                var domObject = objConstructor(row)

                // Append the row as a new data point in data.

                data = data ++ List(domObject)

            }

            scanner.close()

        } catch {

            case e: FileNotFoundException => e.printStackTrace()

        }

        return asJava(data)

    }

}
