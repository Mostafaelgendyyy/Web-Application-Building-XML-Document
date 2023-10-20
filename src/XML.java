import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class XML {
    private static int Record_Num=0;
    static File F_XML;
    static DocumentBuilderFactory XML_Factory = DocumentBuilderFactory.newInstance();
    static DocumentBuilder XML_Builder;
    static Document XML_Document = null;

    static Element XML_Document_Root = null;
    static Scanner input = new Scanner(System.in);
    //    static {
//        try {
//            builder = factory.newDocumentBuilder();
//        } catch (ParserConfigurationException e) {
//            e.printStackTrace();
//        }
//    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    public XML() throws ParserConfigurationException {
        F_XML = new File("src\\Books.xml");
        XML_Factory = DocumentBuilderFactory.newInstance();
        XML_Builder = XML_Factory.newDocumentBuilder();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    public static ArrayList<Book> GetBooksData(int n) throws ParseException {
        ArrayList<Book> Result =new ArrayList<>();
        for (int i=0; i<n ; ++i)
        {
            String ID="BK";
            Node Book;
            if (F_XML.exists() && i==0)
            {
                NodeList BookList= XML_Document.getElementsByTagName("Book");
                Record_Num=BookList.getLength();
            }
            String Recordnum= String.valueOf(++Record_Num);
            if (Recordnum.length()==1)
            {
                ID+="10"+Recordnum;

            }
            else if (Recordnum.length()==2)
            {
                ID+="0"+Recordnum;
            }
            else {
                int NUM= Record_Num+100;
                Recordnum=String.valueOf(NUM);
                ID+=Recordnum;
            }
            System.out.println("====================================================================");
            System.out.println("Enter Data for Book "+(i+1));
            //System.out.print("Enter Book ID: ");
            //String ID= input.next();
            System.out.print("Enter Book Author: ");
            String Author = input.next();
            System.out.print("Enter Book Title: ");
            String Title= input.next();
            System.out.print("Enter Book Genre: ");
            String Genre= input.next();
            System.out.print("Enter Book Price: ");
            double Price= input.nextDouble();
            System.out.print("Enter Book Publish Date 'Year-Month-Day': ");
            String oldstring = input.next();
            Date date =new SimpleDateFormat("yyyy-MM-dd").parse(oldstring);
            System.out.print("Enter Book Description: ");
            String Description = input.next();
            Book Book_added= new Book(ID,Author,Title,Genre,Price,date,Description);
            Result.add(Book_added);

        }
        return Result;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    public static void Addtag(Book b)
    {
        Element Book_Element = XML_Document.createElement("Book");
        Book_Element.setAttribute("ID", b.getID());
        Element Book_Property = null;

        Book_Property = XML_Document.createElement("Author");
        Book_Property.appendChild(XML_Document.createTextNode(b.getAuthor()));
        Book_Element.appendChild(Book_Property);

        Book_Property = XML_Document.createElement("Title");
        Book_Property.appendChild(XML_Document.createTextNode(b.getTitle()));
        Book_Element.appendChild(Book_Property);

        Book_Property = XML_Document.createElement("Genre");
        Book_Property.appendChild(XML_Document.createTextNode(b.getGenre()));
        Book_Element.appendChild(Book_Property);

        Book_Property = XML_Document.createElement("Price");
        double cur = b.getPrice();
        Book_Property.appendChild(XML_Document.createTextNode(Double.toString(cur)));
        Book_Element.appendChild(Book_Property);

        Book_Property = XML_Document.createElement("Publish_Date");
        String DateFormate = new SimpleDateFormat("yyyy-MM-dd").format(b.getPublish_Date());
        Book_Property.appendChild(XML_Document.createTextNode(DateFormate));
        Book_Element.appendChild(Book_Property);

        Book_Property = XML_Document.createElement("Description");
        Book_Property.appendChild(XML_Document.createTextNode(b.getDescription()));
        Book_Element.appendChild(Book_Property);

        XML_Document_Root.appendChild(Book_Element);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    public static void Addrecords() throws IOException, SAXException, TransformerException, ParseException {
        if(F_XML.exists()){
            XML_Document = XML_Builder.parse(new File("src\\Books.xml"));
            XML_Document_Root = (Element) XML_Document.getDocumentElement();
        }else{
            XML_Document = XML_Builder.newDocument();
            XML_Document_Root = XML_Document.createElement("Catalogue");
        }
        System.out.println("Enter number of books");
        int Number_books= input.nextInt();
        ArrayList<Book> books = GetBooksData(Number_books);
        for(int i=0; i<books.size() ;i++){
            Addtag(books.get(i));
        }
        if (!F_XML.exists())
        {
            XML_Document.appendChild(XML_Document_Root);
        }

        DOMSource source = new DOMSource(XML_Document);
        Result result = new StreamResult(F_XML);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(source, result);
        System.out.println("Books are Inserted Successfully");
    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    public ArrayList<Book> Search_By_Title(String T) throws IOException, SAXException, ParseException {
        Document GetFile=XML_Builder.parse(new File("src\\Books.xml"));
        ArrayList<Book> All_Books_Resulted = new ArrayList<>();

        NodeList ChildrenList= GetFile.getDocumentElement().getChildNodes();

        for (int i=0; i<ChildrenList.getLength();++i)
        {
            Node Child= ChildrenList.item(i);

            if (Child.getNodeType()==Node.ELEMENT_NODE)
            {
                Element found= (Element) Child;

                String ID= ((Element) Child).getAttributes().getNamedItem("ID").getNodeValue();

                String Author= found.getElementsByTagName("Author").item(0).getChildNodes().item(0).getNodeValue();

                String Title= found.getElementsByTagName("Title").item(0).getChildNodes().item(0).getNodeValue();
                //System.out.println(T);
                String Genre= found.getElementsByTagName("Genre").item(0).getChildNodes().item(0).getNodeValue();

                Double price = Double.parseDouble(found.getElementsByTagName("Price").item(0).getChildNodes().item(0).getNodeValue());

                String Publish_Date= found.getElementsByTagName("Publish_Date").item(0).getChildNodes().item(0).getNodeValue();
                Date date= new SimpleDateFormat("yyyy-MM-dd").parse(Publish_Date);
                String Description= found.getElementsByTagName("Description").item(0).getChildNodes().item(0).getNodeValue();

                if(T.equals(Title)){
                    All_Books_Resulted.add(new Book(ID,Author,Title,Genre,price,date,Description));
                }
            }
        }
        return All_Books_Resulted;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    public ArrayList<Book> Search_By_Author(String Aut) throws IOException, SAXException, ParseException {
        Document GetFile=XML_Builder.parse(new File("src\\Books.xml"));
        ArrayList<Book> All_Books_Resulted = new ArrayList<>();

        NodeList ChildrenList= GetFile.getDocumentElement().getChildNodes();

        for (int i=0; i<ChildrenList.getLength();++i)
        {
            Node Child= ChildrenList.item(i);

            if (Child.getNodeType()==Node.ELEMENT_NODE)
            {
                Element found= (Element) Child;

                String ID= ((Element) Child).getAttributes().getNamedItem("ID").getNodeValue();

                String Author= found.getElementsByTagName("Author").item(0).getChildNodes().item(0).getNodeValue();

                String Title= found.getElementsByTagName("Title").item(0).getChildNodes().item(0).getNodeValue();
                //System.out.println(T);
                String Genre= found.getElementsByTagName("Genre").item(0).getChildNodes().item(0).getNodeValue();

                Double price = Double.parseDouble(found.getElementsByTagName("Price").item(0).getChildNodes().item(0).getNodeValue());

                String Publish_Date= found.getElementsByTagName("Publish_Date").item(0).getChildNodes().item(0).getNodeValue();
                Date date= new SimpleDateFormat("yyyy-MM-dd").parse(Publish_Date);
                String Description= found.getElementsByTagName("Description").item(0).getChildNodes().item(0).getNodeValue();

                if(Aut.equals(Author)){
                    All_Books_Resulted.add(new Book(ID,Author,Title,Genre,price,date,Description));
                }
            }
        }
        return All_Books_Resulted;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    public void Delete(String ID) throws TransformerException, IOException, SAXException {
        XML_Document = XML_Builder.parse(new File("src\\Books.xml"));
        XML_Document_Root = (Element) XML_Document.getDocumentElement();
        NodeList BookList= XML_Document.getElementsByTagName("Book");
        Boolean flag=true;
        for (int i=0;i<BookList.getLength();++i)
        {
            Node Book= BookList.item(i);
            if (Book.getNodeType()==Node.ELEMENT_NODE)
            {
                Element BookElement= (Element)Book;
                if (BookElement.getAttributeNode("ID").getValue().equals(ID))
                {
                    XML_Document_Root.removeChild(Book);
                    System.out.println("Book is deleted Successfully");
                    flag=false;
                }
            }
        }
        if(flag)
        {
            System.out.println("Book is not Found");
        }
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(XML_Document);
        Result result = new StreamResult(F_XML);
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(source, result);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////
}


