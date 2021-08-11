package cu.cujae.gilsoft.tykeprof.repository;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cu.cujae.gilsoft.tykeprof.app.MainActivity;
import cu.cujae.gilsoft.tykeprof.data.AppDatabase;
import cu.cujae.gilsoft.tykeprof.data.dao.Career_Dao;
import cu.cujae.gilsoft.tykeprof.data.dao.Topic_Dao;
import cu.cujae.gilsoft.tykeprof.data.entity.Career;
import cu.cujae.gilsoft.tykeprof.data.entity.Grant;
import cu.cujae.gilsoft.tykeprof.data.entity.Topic;
import cu.cujae.gilsoft.tykeprof.service.Career_Service;
import cu.cujae.gilsoft.tykeprof.service.Topic_Sevice;
import cu.cujae.gilsoft.tykeprof.util.RetrofitClient;
import cu.cujae.gilsoft.tykeprof.util.UserHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Topic_Repository {

    private Topic_Dao topic_dao;
    private final AppDatabase db;
    private Topic_Sevice topic_sevice;
    private Context context;

    public Topic_Repository(Application application) {
        this.db = AppDatabase.getDatabase(application);
        this.context = application;
        this.topic_dao = db.topic_dao();
        this.topic_sevice = RetrofitClient.getRetrofit().create(Topic_Sevice.class);
    }

    public List<Topic> getAllTopicList() {
        Call<List<Topic>> listCallTopic = topic_sevice.getAllTopicByWeb("Bearer " + UserHelper.getToken(context));
        listCallTopic.enqueue(new Callback<List<Topic>>() {
            @Override
            public void onResponse(Call<List<Topic>> call, Response<List<Topic>> response) {
                if (response.isSuccessful()) {

                    ArrayList<Topic> topicsSave = new ArrayList<>();
                    List<Topic> topicListResponse;
                    topicListResponse = response.body();

                    for (Topic topic : topicListResponse) {
                        Topic topicSave = new Topic(topic.getId_topic(), topic.getName(), topic.getDescrip());
                        topicsSave.add(topicSave);

                        Log.e("Topic ", topicSave.getId_topic() + " " + topicSave.getName() + "" + topicSave.getDescrip());
                    }
                    AppDatabase.databaseWriteExecutor.execute(() -> {
                        topic_dao.deleteAll();
                        topic_dao.saveAllTopic(topicsSave);
                    });
                } else if (response.code() == 403) {
                 //   UserHelper.renovateToken(context);

                } else
                    Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Topic>> call, Throwable t) {
            }
        });
        return topic_dao.getAllTopicList();
    }

    public List<Topic> getAllTopicLocalList() {
        return topic_dao.getAllTopicList();
    }

    public Topic getTopicbyId(long id) {
        return topic_dao.getTopicById(id);
    }

}
