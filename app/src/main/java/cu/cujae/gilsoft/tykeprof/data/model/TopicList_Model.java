package cu.cujae.gilsoft.tykeprof.data.model;

import com.google.gson.annotations.SerializedName;

import cu.cujae.gilsoft.tykeprof.data.entity.Topic;

public class TopicList_Model {

    @SerializedName("tema")
    private Topic topic;

    public TopicList_Model() {
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }
}
