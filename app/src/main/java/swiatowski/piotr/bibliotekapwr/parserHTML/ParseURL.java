package swiatowski.piotr.bibliotekapwr.parserHTML;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import swiatowski.piotr.bibliotekapwr.BookRow;
import swiatowski.piotr.bibliotekapwr.LibraryBook;
import swiatowski.piotr.bibliotekapwr.Page;
import swiatowski.piotr.bibliotekapwr.Rent;

/**
 * Created by Piotrek on 2014-09-02.
 */
public class ParseURL  {

    public List<Rent> parseUrlRent(String infoBookHref) {
        List<Rent> rentList = new ArrayList<Rent>();
        try{
            Document doc  = Jsoup.connect(HtmlConstants.ALEPH_URL + infoBookHref).get();

            Elements line = doc.select("td[class=td1]");

            int i = 0;
            int m = 0;

            String signature = "";
            String status = "";
            String data = "";
            String href = "";

            for (Element l : line) {

                if (i == m +2) {
                    href = getText(l);
                    if (!href.equals("")) {
                        href = getInfoHrefLower(l);
                    }
                    //  href = getInfoHrefLower(l);

                }
                if (i == m + 4) {
                    status = getText(l);
                }
                if ( i == m + 5) {
                    data = getText(l);
                }
                if ( i == m + 8) {
                    signature = getText(l);

                    Log.d("doszlo", " rentt  " + href + "  " + status + "  "  + data + "  " + signature + "");
                    rentList.add(new Rent(signature,status,data,href));
                    href = "";
                    m += 7;
                }
              i++;
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return rentList;
    }

    public BookRow parseUrlInfo(String string) {

        String type = "";
        String institute = "";
        String title = "";
        String year = "";
        String isbn = "";

        try{
            Document doc  = Jsoup.connect(HtmlConstants.ALEPH_URL + string).get();

            Elements line = doc.select("td[class=td1]");
            type = getText(line.get(1));



        } catch (Throwable t) {
            t.printStackTrace();
        }

        return new BookRow(type,institute,title,year,isbn);
    }

    public Page parseUrl(String... strings) {
        List<BookRow> boooks = new ArrayList<BookRow>();
        String nextPage = "";
        boolean ne = false;
        String previousPage = "";
        boolean prev = false;
        try{
            Document doc  = Jsoup.connect(HtmlConstants.ALEPH_URL + strings[0]).get();

            String title = doc.title();

            //get all row with book information
            Elements line = doc.select("tr[valign=baseline]");

            for (Element e : line) {
                // e - yeach row with book data
               Elements td = e.getElementsByTag("td"); // 7 elementow
               String href = getInfoHref(td.get(0)); // get Link infoHref
               String author = getText(td.get(2)); // getAuthor
               String titleBook= getText(td.get(3)); // getTtitle
               String year = getText(td.get(4)); // getYear
               List<LibraryBook> libraryBookList = getLibraryInfo(td.get(5)); // getLibarrayLINk and text ( text podziele)

               boooks.add(new BookRow(href, author,titleBook,year,libraryBookList));


            }

            //getNExt and Previous

            Log.d("doszloP", "try");
            Elements previous = doc.select("a[title=Previous]");

            if (previous.size() > 0) {
                 previousPage = getHrefLower(previous.first());
                 prev = true;
                Log.d("doszloP", previousPage);
            } else {
                Log.d("doszloP"," ooooooooooo");
            }

            Elements next = doc.select("a[title=Next]");
            if (next.size() > 0) {
                 nextPage = getHrefLower(next.first());
                ne = true;
                Log.d("doszloP", nextPage);
            } else {
                Log.d("doszloP"," next no");
            }

        } catch (Throwable t) {
            t.printStackTrace();
        }

        return new Page(boooks, nextPage, previousPage, prev, ne);
    }

    private List<LibraryBook> getLibraryInfo(Element element) {
        List<LibraryBook> libraryBookList = new ArrayList<LibraryBook>();
        Elements libraries = element.getElementsByTag("A");
        for (Element library : libraries) {
            String href = getHref(library);
            String name = getText(library);
            libraryBookList.add(new LibraryBook(name, href));
        }

        return libraryBookList;
    }

    private String getInfoHref(Element element) {
        return getHref(element.getElementsByTag("A").first());
    }

    private String getInfoHrefLower(Element element) {
        return getHrefLower(element.getElementsByTag("a").first());
    }

    private String getHref(Element element) {
        if (element.hasAttr("HREF")) {
            return element.attr("HREF");
        }
        return "";
    }
    private String getHrefLower(Element element) {
        if (element.hasAttr("href")) {
            return element.attr("href");
        }
        return "";
    }

    private String getText(Element element) {
        return element.text();
    }



}
