package swiatowski.piotr.bibliotekapwr.parserHTML;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import swiatowski.piotr.bibliotekapwr.db.entity.BookEntity;
import swiatowski.piotr.bibliotekapwr.LibraryBook;
import swiatowski.piotr.bibliotekapwr.Page;
import swiatowski.piotr.bibliotekapwr.Rent;

/**
 * Created by Piotrek on 2014-09-02.
 */
public class ParseURL  {

    public List<Rent> parseUrlRent(String infoBookHref) {
        List<Rent> rentList = new ArrayList<Rent>();
        try {
            Document doc = Jsoup.connect(HtmlConstants.ALEPH_URL + infoBookHref).get();
            Elements line = doc.select("td[class=td1]");

            int i = 0;
            int m = 0;

            String signature = "";
            String status = "";
            String data = "";
            String href = "";

            for (Element l : line) {

                if (i == m + 2) {
                    href = getText(l);
                    if (!href.equals("")) {
                        href = getInfoHrefLower(l);
                    }
                }
                if (i == m + 4) {
                    status = getText(l);
                }
                if (i == m + 5) {
                    data = getText(l);
                }
                if (i == m + 8) {
                    signature = getText(l);

                    rentList.add(new Rent(signature, status, data, href));
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

    public BookEntity parseUrlInfo(String string) {
        String type = "";
        String institute = "";
        String title = "";
        String year = "";
        String isbn = "";

        try {
            Document doc = Jsoup.connect(HtmlConstants.ALEPH_URL + string).get();

            Elements line = doc.select("td[class=td1]");
            type = getText(line.get(1));
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return new BookEntity(type, institute, title, year, isbn);
    }

    public Page parseUrl(String... strings) {
        List<BookEntity> boooks = new ArrayList<BookEntity>();
        String nextPage = "";
        boolean ne = false;
        String previousPage = "";
        boolean prev = false;

        try {
            Document doc = Jsoup.connect(HtmlConstants.ALEPH_URL + strings[0]).get();
            Elements line = doc.select("tr[valign=baseline]");

            for (Element e : line) {
                boooks.add(getBookRowData(e));
            }

            Elements previous = doc.select("a[title=Previous]");
            if (previous.size() > 0) {
                previousPage = getHrefLower(previous.first());
                prev = true;
            }
            Elements next = doc.select("a[title=Next]");
            if (next.size() > 0) {
                nextPage = getHrefLower(next.first());
                ne = true;
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }

        Log.d("doszlo", "next= " + nextPage + "  " + ne + "    pre " + previousPage + "  " + prev);

        return new Page(boooks, nextPage, previousPage, prev, ne);
    }

    private BookEntity getBookRowData(Element e) {

        Elements td = e.getElementsByTag("td");
        String href = getInfoHref(td.get(0));
        String author = getText(td.get(2));
        String titleBook = getText(td.get(3));
        String year = getText(td.get(4));
        List<LibraryBook> libraryBookList = getLibraryInfo(td.get(5));

        return new BookEntity(href, author, titleBook, year, libraryBookList);
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
