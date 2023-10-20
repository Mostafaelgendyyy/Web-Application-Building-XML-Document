/****************************************************
 * Names and IDS for Students:                      *
 * 1- Farida Yehia Abdelaziz Labib    ---> 20190379 *
 * 2- Mostafa Mohamed Mostafa ElGendy ---> 20190543 *
 *                 Group : IS-S3                    *
 ****************************************************/
import java.util.ArrayList;
import java.util.Scanner;


public class Main{
    public static void main(String[] args) {
        try {
            Scanner Input = new Scanner(System.in);
            while(true)
            {
                System.out.println("1- Add Record In XML File");
                System.out.println("2- Search for Record In XML File");
                System.out.println("3- Delete Record In XML File");
                System.out.println("4- Exit");
                System.out.println("Enter your choice");
                String choice = Input.nextLine();
                XML XMLfile= new XML();
                if(choice.equals("1")){
                    XMLfile.Addrecords();
                }
                else if (choice.equals("2"))
                {
                    System.out.println("1- Search By Title");
                    System.out.println("2- Search By Author");
                    System.out.println("Enter your choice");

                    String Choice_2= Input.nextLine();
                    if(Choice_2.equals("1"))
                    {
                        System.out.println("Enter the Title:");
                        String Title_to_Search = Input.nextLine();
                        ArrayList<Book> Result= XMLfile.Search_By_Title(Title_to_Search);
                        System.out.println("Therer are "+ Result.size()+" Results with the Title Name "+ Title_to_Search);
                        for(Book b: Result)
                        {
                            System.out.println("====================================================================");
                            b.Print_Book();
                        }
                    }
                    else if(Choice_2.equals("2")) {
                        System.out.println("Enter the Author Name:");
                        String Author_to_Search = Input.nextLine();
                        ArrayList<Book> Result= XMLfile.Search_By_Author(Author_to_Search);
                        System.out.println("There are "+ Result.size()+" Results with the Author Name "+ Author_to_Search);
                        for(Book b: Result)
                        {
                            System.out.println("====================================================================");

                            b.Print_Book();
                        }
                    }
                }

                else if (choice.equals("3"))
                {
                    System.out.println("Enter the ID of Book you want to Delete: ");
                    String ID=Input.nextLine();
                    XMLfile.Delete(ID);
                }
                else if (choice.equals("4"))
                {
                    break;
                }
                else
                {
                    System.out.println("Wrong Input Try again");
                }
                System.out.println("==================================================================================");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


//        String oldstring = "2011-01-18";
//        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(oldstring);
//        String newstring = new SimpleDateFormat("yyyy-MM-dd").format(date);
//        System.out.println(newstring);