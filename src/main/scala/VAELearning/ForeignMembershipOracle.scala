package VAELearning

import algebralearning.oracles.MembershipOracle
import utilities.Pair
import java.io.PrintStream
import java.lang.Double.doubleToLongBits
import java.net.Socket
import scala.io.BufferedSource

class ForeignMembershipOracle[DomType](ip : String, port : Short) extends MembershipOracle[DomType] {

    private var sock : Socket         = null
    private var sink : PrintStream    = null
    private var recv : BufferedSource = null

    def start(): Unit = {

        sock = new Socket(ip, port)
        sink = new PrintStream(sock.getOutputStream)
        recv = new BufferedSource(sock.getInputStream)

    }

    private def serialize_character(value: Character) : List[Byte] = List(

        value.asInstanceOf[Byte]

    )

    private def serialize_integer(value: Integer) : List[Byte] = List(

        ((value >> 24) & 0xff).asInstanceOf[Byte],
        ((value >> 16) & 0xff).asInstanceOf[Byte],
        ((value >>  8) & 0xff).asInstanceOf[Byte],
        (value         & 0xff).asInstanceOf[Byte],

    )

    private def serialize_double(value: Double): List[Byte] = List(

        ((doubleToLongBits(value) >> 56) & 0xff).asInstanceOf[Byte],
        ((doubleToLongBits(value) >> 48) & 0xff).asInstanceOf[Byte],
        ((doubleToLongBits(value) >> 40) & 0xff).asInstanceOf[Byte],
        ((doubleToLongBits(value) >> 32) & 0xff).asInstanceOf[Byte],
        ((doubleToLongBits(value) >> 24) & 0xff).asInstanceOf[Byte],
        ((doubleToLongBits(value) >> 16) & 0xff).asInstanceOf[Byte],
        ((doubleToLongBits(value) >>  8) & 0xff).asInstanceOf[Byte],
        (doubleToLongBits(value)         & 0xff).asInstanceOf[Byte],

    )

    def serialize_domain_object(value: Any): List[Byte] = value match {

        case value: Character          => serialize_character(value)
        case value: Integer            => serialize_integer(value)
        case value: java.lang.Double   => serialize_double(value)
        case value: Pair[Any, Any]     => List.concat(serialize_domain_object(value.first), serialize_domain_object(value.second))
        case _                         => List()

    }

    override def query(input: DomType): Boolean = {

        var data = serialize_domain_object(input).toArray

        sink.write(data)
        sink.flush()

        while (recv.hasNext) {

            println(recv.next())

        }

        return true

    }

    def stop(): Unit = {

        recv.close()
        sink.close()
        sock.close()

        recv = null
        sink = null
        sock = null

    }

}
