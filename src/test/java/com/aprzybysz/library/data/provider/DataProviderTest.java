package com.aprzybysz.library.data.provider;

import com.aprzybysz.library.data.model.Book;
import com.aprzybysz.library.data.provider.DataProvider;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class DataProviderTest {

  @Test
  public void regexTestShouldReturnTrueForAbsoluteFilePaths() {
    String file1 = "D:\\abcd.json";
    String file2 = "C:\\user\\abcd.json";
    String file3 = "abcd";
    String file4 = "abgd.xml";
    String file5 = "abgd.json";

    assertTrue(Pattern.matches(DataProvider.JSON_FILE_REGEX, file1));
    assertTrue(Pattern.matches(DataProvider.JSON_FILE_REGEX, file2));
    assertFalse(Pattern.matches(DataProvider.JSON_FILE_REGEX, file3));
    assertFalse(Pattern.matches(DataProvider.JSON_FILE_REGEX, file4));
    assertFalse(Pattern.matches(DataProvider.JSON_FILE_REGEX, file5));
  }

  @Test
  void shouldParseJsonStringAndReturnABook() {
    Gson gson = new Gson();
    String oneBook = "{\n" + "   \"kind\": \"books#volume\",\n" + "   \"id\": \"7tkN1CYzn2cC\",\n" + "   \"etag\": \"pfjjxSpetIM\",\n" + "   \"selfLink\": \"https://www.googleapis.com/books/v1/volumes/7tkN1CYzn2cC\",\n" + "   \"volumeInfo\": {\n" + "    \"title\": \"A Hypervista of the Java Landscape\",\n" + "    \"publisher\": \"InfoStrategist.com\",\n" + "    \"industryIdentifiers\": [\n" + "     {\n" + "      \"type\": \"ISBN_13\",\n" + "      \"identifier\": \"9781592432172\"\n" + "     },\n" + "     {\n" + "      \"type\": \"ISBN_10\",\n" + "      \"identifier\": \"1592432174\"\n" + "     }\n" + "    ],\n" + "    \"readingModes\": {\n" + "     \"text\": true,\n" + "     \"image\": true\n" + "    },\n" + "    \"printType\": \"BOOK\",\n" + "    \"maturityRating\": \"NOT_MATURE\",\n" + "    \"allowAnonLogging\": false,\n" + "    \"contentVersion\": \"1.0.1.0.preview.3\",\n" + "    \"imageLinks\": {\n" + "     \"smallThumbnail\": \"http://books.google.com/books/content?id=7tkN1CYzn2cC&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api\",\n" + "     \"thumbnail\": \"http://books.google.com/books/content?id=7tkN1CYzn2cC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api\"\n" + "    },\n" + "    \"language\": \"en\",\n" + "    \"previewLink\": \"http://books.google.pl/books?id=7tkN1CYzn2cC&pg=PP1&dq=java&hl=&cd=1&source=gbs_api\",\n" + "    \"infoLink\": \"http://books.google.pl/books?id=7tkN1CYzn2cC&dq=java&hl=&source=gbs_api\",\n" + "    \"canonicalVolumeLink\": \"https://books.google.com/books/about/A_Hypervista_of_the_Java_Landscape.html?hl=&id=7tkN1CYzn2cC\"\n" + "   },\n" + "   \"saleInfo\": {\n" + "    \"country\": \"PL\",\n" + "    \"saleability\": \"NOT_FOR_SALE\",\n" + "    \"isEbook\": false\n" + "   },\n" + "   \"accessInfo\": {\n" + "    \"country\": \"PL\",\n" + "    \"viewability\": \"PARTIAL\",\n" + "    \"embeddable\": true,\n" + "    \"publicDomain\": false,\n" + "    \"textToSpeechPermission\": \"ALLOWED\",\n" + "    \"epub\": {\n" + "     \"isAvailable\": true,\n" + "     \"acsTokenLink\": \"http://books.google.pl/books/download/A_Hypervista_of_the_Java_Landscape-sample-epub.acsm?id=7tkN1CYzn2cC&format=epub&output=acs4_fulfillment_token&dl_type=sample&source=gbs_api\"\n" + "    },\n" + "    \"pdf\": {\n" + "     \"isAvailable\": true,\n" + "     \"acsTokenLink\": \"http://books.google.pl/books/download/A_Hypervista_of_the_Java_Landscape-sample-pdf.acsm?id=7tkN1CYzn2cC&format=pdf&output=acs4_fulfillment_token&dl_type=sample&source=gbs_api\"\n" + "    },\n" + "    \"webReaderLink\": \"http://play.google.com/books/reader?id=7tkN1CYzn2cC&hl=&printsec=frontcover&source=gbs_api\",\n" + "    \"accessViewStatus\": \"SAMPLE\",\n" + "    \"quoteSharingAllowed\": false\n" + "   }\n" + "  }";
    JsonObject root = gson.fromJson(oneBook, JsonObject.class);
    Book book = DataProvider.getInstance().extractBookFromJsonBookElement(root);
    testBook(book);
  }

  private void testBook(Book book) {
    assertEquals("9781592432172", book.getIsbn());
    assertEquals("A Hypervista of the Java Landscape", book.getTitle());
    assertNull(book.getSubtitle());
    assertEquals("InfoStrategist.com", book.getPublisher());
    assertNull(book.getPublishedDate());
    assertNull(book.getDescription());
    assertNull(book.getPageCount());
    assertEquals("http://books.google.com/books/content?id=7tkN1CYzn2cC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
        book.getThumbnailUrl());
    assertEquals("en", book.getLanguage());
    assertEquals("http://books.google.pl/books?id=7tkN1CYzn2cC&pg=PP1&dq=java&hl=&cd=1&source=gbs_api",
        book.getPreviewLink());
    assertNull(book.getAverageRating());
    assertNull(book.getRatingsCount());
    assertNull(book.getAuthors());
    assertNull(book.getCategories());
  }
}