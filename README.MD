# Content-Extractor

Java library.

Returns the selector with the largest amount of content.


### `jsoup` should be present in classpath.

### Example:

```java
import com.github.truerss.ContentExtractor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Document;

String url = "http://example.com/post.html";
Document doc = Jsoup.connect(url).get();
Element body = doc.body();
ExtractResult result = ContentExtractor.extract(body);
System.out.println("==========> " + result.selector);

```

### License: MIT




