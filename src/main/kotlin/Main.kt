
import org.json.JSONObject
import org.json.XML
import java.io.File
import javax.xml.XMLConstants
import javax.xml.transform.stream.StreamSource
import javax.xml.validation.SchemaFactory

fun validateXML(file: File) {
    val factory: SchemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
    val xsdFile = File("ab.xsd")
    try {
        val schema = factory.newSchema(xsdFile)
        val validator = schema.newValidator()
        validator.validate(StreamSource(file))
        println("Valid XML!")
    } catch (e: Exception) {
        println("Invalid XML!" +
                "\n ${e.message}")
    }
}

fun main(args: Array<String>) {
    if (args[0] == "-x") { // Parse XML to JSON
        val xmlFile = File(args[1])
        val xml = xmlFile.readText()
        val jsonObj = XML.toJSONObject(xml.trimIndent())
        val jsonStr: String = jsonObj.toString(2)
        println(jsonStr)
        File("output.json").writeText(jsonStr)
        println("Output saved as output.json")
        validateXML(xmlFile)

    } else if (args[0] == "-j") { // Parse JSON to XML
        val jsonStr = File(args[1]).readText()
        val jsonObj = JSONObject(jsonStr)
        val xml = XML.toString(jsonObj)
        println(xml)
        val xmlFile = File("output.xml")
        File("output.xml").writeText(xml)
        println("Output saved as output.xml")
        validateXML(xmlFile)
    }
}