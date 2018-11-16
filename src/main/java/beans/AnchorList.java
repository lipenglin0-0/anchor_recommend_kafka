package beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties({
    "errmsg",
    "errno"
})
public class AnchorList {
  private Data data;

  public List<Anchor> getData() {
    return data.items;
  }

  public void setData(Data data) {
    this.data = data;
  }

  @JsonIgnoreProperties({
      "offsetNum",
      "total",
      "anchorRank",
      "highLightNum",
      "sliderdata"
  })
  private static class Data {
    private List<Anchor> items;

    public List<Anchor> getItems() {
      return items;
    }

    public void setItems(List<Anchor> items) {
      this.items = items;
    }
  }

  @JsonIgnoreProperties({
      "host_level_info",
      "is_top",
      "ishighlight",
      "julyMedalNum",
      "label",
      "medalNum",
      "pictures",
      "pkinfo",
      "rollinfo",
      "room_key",
      "room_type",
      "roomkey",
      "rtype_value",
      "status",
      "tag",
      "tag_color",
      "tag_switch",
      "top_description",
      "top_icon",
      "xid",
      "dakki"
  })
  public static class Anchor {
    private Classification classification;
    private String id;
    private String name;
    @JsonProperty(value = "person_num")
    private Integer personNum;
    @JsonProperty(value = "ticket_rank_info")
    private Ticket ticket;

    public Classification getClassification() {
      return classification;
    }

    public void setClassification(Classification classification) {
      this.classification = classification;
    }

    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public Integer getPersonNum() {
      return personNum;
    }

    public void setPersonNum(Integer personNum) {
      this.personNum = personNum;
    }

    public Ticket getTicket() {
      return ticket;
    }

    public void setTicket(Ticket ticket) {
      this.ticket = ticket;
    }

    public Info getInfo() {
      return info;
    }

    public void setInfo(Info info) {
      this.info = info;
    }

    @JsonProperty(value = "userinfo")
    private Info info;

    private static class Ticket {
      private Integer rank;
      private Integer score;

      public Integer getRank() {
        return rank;
      }

      public void setRank(Integer rank) {
        this.rank = rank;
      }

      public Integer getScore() {
        return score;
      }

      public void setScore(Integer score) {
        this.score = score;
      }
    }

    @JsonIgnoreProperties({
        "avatar",
        "rid",
        "userName",
        "retainHash"
    })
    private static class Info {
      private String nickName;

      public String getNickName() {
        return nickName;
      }

      public void setNickName(String nickName) {
        this.nickName = nickName;
      }
    }

    @Override public String toString() {
      return "room: " + this.name + "|anchor: " + this.info.nickName + "|personNum: " + this.personNum;
    }
  }

  private static class Classification {
    private String cname;
    private String ename;

    public String getCname() {
      return cname;
    }

    public void setCname(String cname) {
      this.cname = cname;
    }

    public String getEname() {
      return ename;
    }

    public void setEname(String ename) {
      this.ename = ename;
    }
  }
}
