package lt.brick1;

import java.util.Arrays;
import java.util.List;

public class HomePage {
  private String message = "Sveiku<b>čiai</b>";
  private List<Chebras> chebra = Arrays.asList(
          new Chebras("Saulytė"),
          new Chebras("Jonė"),
          new Chebras("Saulius"));

  public static class Chebras {
    private String name;

    public Chebras(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }
  }

  public String getMessage() {
    return message;
  }

  public List<Chebras> getChebra() {
    return chebra;
  }
}
