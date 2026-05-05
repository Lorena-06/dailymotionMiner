
package aiss.dailymotion_Miner.model.dailymotion;

import java.io.Serializable;

import javax.annotation.processing.Generated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "page",
    "limit",
    "explicit",
    "has_more",
    "list"
})
@Generated("jsonschema2pojo")
public class ChannelResponse implements Serializable
{

    @JsonProperty("page")
    private Integer page;
    @JsonProperty("limit")
    private Integer limit;
    @JsonProperty("explicit")
    private Boolean explicit;
    @JsonProperty("has_more")
    private Boolean hasMore;
    @JsonProperty("list")
    private java.util.List<aiss.dailymotion_Miner.model.dailymotion.Channel> list;
    private final static long serialVersionUID = -3206999977597365449L;

    @JsonProperty("page")
    public Integer getPage() {
        return page;
    }

    @JsonProperty("page")
    public void setPage(Integer page) {
        this.page = page;
    }

    @JsonProperty("limit")
    public Integer getLimit() {
        return limit;
    }

    @JsonProperty("limit")
    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    @JsonProperty("explicit")
    public Boolean getExplicit() {
        return explicit;
    }

    @JsonProperty("explicit")
    public void setExplicit(Boolean explicit) {
        this.explicit = explicit;
    }

    @JsonProperty("has_more")
    public Boolean getHasMore() {
        return hasMore;
    }

    @JsonProperty("has_more")
    public void setHasMore(Boolean hasMore) {
        this.hasMore = hasMore;
    }

    @JsonProperty("list")
    public java.util.List<aiss.dailymotion_Miner.model.dailymotion.Channel> getList() {
        return list;
    }

    @JsonProperty("list")
    public void setList(java.util.List<aiss.dailymotion_Miner.model.dailymotion.Channel> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Channel.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("page");
        sb.append('=');
        sb.append(((this.page == null)?"<null>":this.page));
        sb.append(',');
        sb.append("limit");
        sb.append('=');
        sb.append(((this.limit == null)?"<null>":this.limit));
        sb.append(',');
        sb.append("explicit");
        sb.append('=');
        sb.append(((this.explicit == null)?"<null>":this.explicit));
        sb.append(',');
        sb.append("hasMore");
        sb.append('=');
        sb.append(((this.hasMore == null)?"<null>":this.hasMore));
        sb.append(',');
        sb.append("list");
        sb.append('=');
        sb.append(((this.list == null)?"<null>":this.list));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
