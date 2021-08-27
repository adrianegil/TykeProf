package cu.cujae.gilsoft.tykeprof.app.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.slider.Slider;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import cu.cujae.gilsoft.tykeprof.R;
import cu.cujae.gilsoft.tykeprof.adapter.Group_Adapter;
import cu.cujae.gilsoft.tykeprof.adapter.QuestionOfStrategy_Adapter;
import cu.cujae.gilsoft.tykeprof.adapter.TopicFinish_Adapter;
import cu.cujae.gilsoft.tykeprof.adapter.Topic_Adapter;
import cu.cujae.gilsoft.tykeprof.data.entity.Group;
import cu.cujae.gilsoft.tykeprof.data.entity.Question;
import cu.cujae.gilsoft.tykeprof.data.entity.Topic;
import cu.cujae.gilsoft.tykeprof.databinding.NewStrategy2stepFragmentBinding;
import cu.cujae.gilsoft.tykeprof.databinding.NewStrategy3stepFragmentBinding;
import cu.cujae.gilsoft.tykeprof.databinding.NewStrategy4stepFragmentBinding;
import cu.cujae.gilsoft.tykeprof.databinding.NewStrategyFragmentBinding;
import cu.cujae.gilsoft.tykeprof.service.Group_Service;
import cu.cujae.gilsoft.tykeprof.service.Question_Service;
import cu.cujae.gilsoft.tykeprof.service.Topic_Sevice;
import cu.cujae.gilsoft.tykeprof.util.RetrofitClient;
import cu.cujae.gilsoft.tykeprof.util.ToastHelper;
import cu.cujae.gilsoft.tykeprof.util.UserHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewStrategyFragment extends Fragment {

    private NewStrategyFragmentBinding newStrategyFragmentBinding;
    private NewStrategy2stepFragmentBinding newStrategy2stepFragmentBinding;
    private NewStrategy3stepFragmentBinding newStrategy3stepFragmentBinding;
    private NewStrategy4stepFragmentBinding newStrategy4stepFragmentBinding;

    private NavController newStrategyNavController;
    private NavController mainNavController;

    private Topic_Adapter topic_adapter;
    private Group_Adapter group_adapter;
    private QuestionOfStrategy_Adapter questionOfStrategy_adapter;
    private TopicFinish_Adapter topicFinishAdapter;

    private RecyclerView recyclerViewTopics;
    private RecyclerView recyclerViewGroups;
    private RecyclerView recyclerQuestionsAvailable;
    private RecyclerView recyclerViewTopicFinish;

    private Group_Service group_service;
    private Topic_Sevice topic_sevice;
    private Question_Service question_service;

    private int step;
    private String subjectName;

    public NewStrategyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = null;
        newStrategyFragmentBinding = NewStrategyFragmentBinding.inflate(inflater, container, false);
        newStrategy2stepFragmentBinding = NewStrategy2stepFragmentBinding.inflate(inflater, container, false);
        newStrategy3stepFragmentBinding = NewStrategy3stepFragmentBinding.inflate(inflater, container, false);
        newStrategy4stepFragmentBinding = NewStrategy4stepFragmentBinding.inflate(inflater, container, false);
        step = getArguments().getInt("stepNumber");

        //BRINDAR LA VISTA CORRESPONDIENTE AL PASO DONDE SE ENCUENTRE EL USUARIO
        switch (step) {
            case 1:
                root = newStrategyFragmentBinding.getRoot();
                break;
            case 2:
                root = newStrategy2stepFragmentBinding.getRoot();
                break;
            case 3:
                root = newStrategy3stepFragmentBinding.getRoot();
                break;
            case 4:
                root = newStrategy4stepFragmentBinding.getRoot();
                break;
        }
        return root;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //CONTROLADORES DE NAVEGACIÓN
        newStrategyNavController = Navigation.findNavController(getActivity(), R.id.newStrategyNavHostFragment);
        mainNavController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_container);

        //INICIALIZAR DATOS
        init(step);

        //CHEQUEAR CAMBIOS EN EL NOMBRE DE LA ASIGNATURA
        newStrategy2stepFragmentBinding.autoCompleteSubjectOfNewStrategy.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                subjectName = (String) newStrategy2stepFragmentBinding.autoCompleteSubjectOfNewStrategy.getAdapter().getItem(position);
                HomeNewStrategyFragment.strategy_model.setSubjectName(subjectName);
                getQuestionBySubjectName(subjectName);
                changeGroupAdapter(subjectName);
                changeTopicAdapter(subjectName);
            }
        });

        // ESCUCHAR CAMBIOS EN EL SLIDER CORRESPONDIETE A LOS PUNTOS DE LA ESTRATEGIA
        newStrategyFragmentBinding.sliderStrategyPoints.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                newStrategyFragmentBinding.sliderStrategyPoints.setValue((int) slider.getValue());
                newStrategyFragmentBinding.textViewStrategyPoints.setText(getString(R.string.points) + ": " + (int) slider.getValue());
            }
        });

        //IR AL PASO 2
        newStrategyFragmentBinding.floatingActionButtonNext2Step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strategyName = newStrategyFragmentBinding.editTextStrategyName.getText().toString();

                if (strategyName.isEmpty()) {
                    newStrategyFragmentBinding.textInputLayoutStrategyName.setError(getString(R.string.required));
                } else {
                    HomeNewStrategyFragment.strategy_model.setName(strategyName);
                    HomeNewStrategyFragment.strategy_model.setCantPoints((int) newStrategyFragmentBinding.sliderStrategyPoints.getValue());
                    HomeNewStrategyFragment.strategy_model.setEnabled(newStrategyFragmentBinding.checkBoxEnabled.isChecked());
                    HomeNewStrategyFragment.strategy_model.setEvaluated(newStrategyFragmentBinding.checkBoxEvaluated.isChecked());
                    newStrategyNavController.navigate(R.id.go_newStrategy2Step);
                    HomeNewStrategyFragment.stepViewNewStrategy.go(1, true);
                }

            }
        });

        //REGRESAR AL PASO 1
        newStrategy2stepFragmentBinding.floatingActionButtonBack1Step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newStrategyNavController.navigate(R.id.go_newStrategyBack1Step);
                HomeNewStrategyFragment.stepViewNewStrategy.go(0, true);
            }
        });

        //IR AL PASO 3
        newStrategy2stepFragmentBinding.floatingActionButtonNext3Step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subjectName = newStrategy2stepFragmentBinding.autoCompleteSubjectOfNewStrategy.getText().toString();
                if (subjectName.isEmpty()) {
                    newStrategy2stepFragmentBinding.textInputLayoutSubjectOfNewStrategy.setError(getString(R.string.required));
                } else {
                    HomeNewStrategyFragment.strategy_model.setSubjectName(subjectName);
                    addTopicsAndGroups();
                    if (HomeNewStrategyFragment.strategy_model.getGroupsList().isEmpty() || HomeNewStrategyFragment.strategy_model.getTopicsList().isEmpty()) {
                        if (HomeNewStrategyFragment.strategy_model.getTopicsList().isEmpty())
                            ToastHelper.showCustomToast(getActivity(), "error", "Debe seleccionar Temas");
                        if (HomeNewStrategyFragment.strategy_model.getGroupsList().isEmpty())
                            ToastHelper.showCustomToast(getActivity(), "error", "Debe seleccionar Grupos");
                    } else {
                        newStrategyNavController.navigate(R.id.go_newStrategy3Step);
                        HomeNewStrategyFragment.stepViewNewStrategy.go(2, true);
                    }
                }
            }
        });

        //REGRESAR AL PASO 2
        newStrategy3stepFragmentBinding.floatingActionButtonBack2Step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newStrategyNavController.navigate(R.id.go_newStrategyBack2Step);
                HomeNewStrategyFragment.stepViewNewStrategy.go(1, true);
            }
        });

        //IR AL PASO 4
        newStrategy3stepFragmentBinding.floatingActionButtonNext4Step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addQuestions();
                if (HomeNewStrategyFragment.strategy_model.getQuestionsList().isEmpty()) {
                    if (HomeNewStrategyFragment.strategy_model.getQuestionsList().isEmpty())
                        ToastHelper.showCustomToast(getActivity(), "error", "Debe seleccionar Preguntas");
                } else {
                    newStrategyNavController.navigate(R.id.go_newStrategy4Step);
                    HomeNewStrategyFragment.stepViewNewStrategy.go(3, true);
                }
            }
        });

        //REGRESAR AL PASO 3
        newStrategy4stepFragmentBinding.floatingActionButtonBack3Step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newStrategyNavController.navigate(R.id.go_newStrategyBack3Step);
                HomeNewStrategyFragment.stepViewNewStrategy.go(2, true);
            }
        });

        //REGRESAR AL PASO 3
        newStrategy4stepFragmentBinding.materialButtonFinishNewStrategy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainNavController.navigate(R.id.nav_strategyFragment);
            }
        });

        //FINALIZAR LA CREACIÓN DE UNA NUEVA PREGUNTA
        newStrategy4stepFragmentBinding.materialButtonFinishNewStrategy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeNewStrategyFragment.homeNewStrategyViewModel.saveStrategy(HomeNewStrategyFragment.strategy_model);
                mainNavController.popBackStack(R.id.nav_strategyFragment, false);
            }
        });

    }

    //INICIAR COMPONENTES VISUALES EN DEPENDENCIA DEL PASO DONDE SE ENCUENTRE EL USUARIO
    @SuppressLint("SetTextI18n")
    public void init(int step) {

        switch (step) {
            case 1:
                try {
                    newStrategyFragmentBinding.editTextStrategyName.setText(HomeNewStrategyFragment.strategy_model.getName());
                    newStrategyFragmentBinding.textViewStrategyPoints.setText(getString(R.string.points) + ": " + HomeNewStrategyFragment.strategy_model.getCantPoints());
                    newStrategyFragmentBinding.sliderStrategyPoints.setValue(HomeNewStrategyFragment.strategy_model.getCantPoints());
                    newStrategyFragmentBinding.checkBoxEvaluated.setChecked(HomeNewStrategyFragment.strategy_model.getEvaluated());
                    newStrategyFragmentBinding.checkBoxEnabled.setChecked(HomeNewStrategyFragment.strategy_model.getEnabled());
                } catch (Exception e) {
                    newStrategyFragmentBinding.editTextStrategyName.setText("");
                    newStrategyFragmentBinding.textViewStrategyPoints.setText(getString(R.string.points) + ": ");
                    newStrategyFragmentBinding.sliderStrategyPoints.setValue(10);
                    newStrategyFragmentBinding.checkBoxEvaluated.setChecked(false);
                    newStrategyFragmentBinding.checkBoxEnabled.setChecked(false);
                }
                break;
            case 2:
                group_service = RetrofitClient.getRetrofit().create(Group_Service.class);
                topic_sevice = RetrofitClient.getRetrofit().create(Topic_Sevice.class);
                question_service = RetrofitClient.getRetrofit().create(Question_Service.class);
                recyclerViewTopics = newStrategy2stepFragmentBinding.recyclerViewTopics;
                recyclerViewGroups = newStrategy2stepFragmentBinding.recyclerViewGroups;
                LinearLayoutManager managerTopic = new LinearLayoutManager(getContext());
                managerTopic.setOrientation(RecyclerView.HORIZONTAL);
                LinearLayoutManager managerGroup = new LinearLayoutManager(getContext());
                managerGroup.setOrientation(RecyclerView.HORIZONTAL);
                recyclerViewTopics.setLayoutManager(managerTopic);
                recyclerViewTopics.setHasFixedSize(true);
                recyclerViewGroups.setLayoutManager(managerGroup);
                recyclerViewGroups.setHasFixedSize(true);
                topic_adapter = new Topic_Adapter(HomeNewStrategyFragment.topicList, getContext());
                recyclerViewTopics.setAdapter(topic_adapter);
                group_adapter = new Group_Adapter(HomeNewStrategyFragment.groupList, getContext());
                recyclerViewGroups.setAdapter(group_adapter);
                try {
                    ArrayList<String> subjects = (ArrayList<String>) HomeNewStrategyFragment.homeNewStrategyViewModel.teacherSubjectsModel.getSubjects();
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, subjects);
                    newStrategy2stepFragmentBinding.autoCompleteSubjectOfNewStrategy.setText(HomeNewStrategyFragment.strategy_model.getSubjectName());
                    newStrategy2stepFragmentBinding.autoCompleteSubjectOfNewStrategy.setAdapter(arrayAdapter);
                    HomeNewStrategyFragment.strategy_model.setId_teacher(HomeNewStrategyFragment.homeNewStrategyViewModel.teacherSubjectsModel.getId_teacher());

                } catch (Exception e) {
                    newStrategy2stepFragmentBinding.autoCompleteSubjectOfNewStrategy.setText("");
                }
                break;
            case 3:
                recyclerQuestionsAvailable = newStrategy3stepFragmentBinding.recyclerViewQuestionsOfStrategy;
                recyclerQuestionsAvailable.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerQuestionsAvailable.setHasFixedSize(true);
                questionOfStrategy_adapter = new QuestionOfStrategy_Adapter(HomeNewStrategyFragment.questionList, getActivity());
                recyclerQuestionsAvailable.setAdapter(questionOfStrategy_adapter);
                break;
            case 4:
                newStrategy4stepFragmentBinding.textViewStrategyNameFinish.setText(getString(R.string.strategy_name) + ": " + HomeNewStrategyFragment.strategy_model.getName());
                newStrategy4stepFragmentBinding.textViewCantsPointsFinish.setText(getString(R.string.points) + ": " + HomeNewStrategyFragment.strategy_model.getCantPoints());
                newStrategy4stepFragmentBinding.textViewSubjectFinish.setText(getString(R.string.subject) + ": " + HomeNewStrategyFragment.strategy_model.getSubjectName());

                if (HomeNewStrategyFragment.strategy_model.getEnabled())
                    newStrategy4stepFragmentBinding.textViewEnabledFinish.setText(getString(R.string.enabled) + ": " + getString(R.string.yes));
                else
                    newStrategy4stepFragmentBinding.textViewEnabledFinish.setText(getString(R.string.enabled) + ": No");

                if (HomeNewStrategyFragment.strategy_model.getEvaluated())
                    newStrategy4stepFragmentBinding.textViewEvaluatedFinish.setText(getString(R.string.evaluated) + ": " + getString(R.string.yes));
                else
                    newStrategy4stepFragmentBinding.textViewEvaluatedFinish.setText(getString(R.string.evaluated) + ": No");

                newStrategy4stepFragmentBinding.textViewCantsGroupFinish.setText(getString(R.string.group_cant) + " " + HomeNewStrategyFragment.strategy_model.getGroupsList().size());
                newStrategy4stepFragmentBinding.textViewCantsQuestionsFinish.setText(getString(R.string.question_cant) + " " + HomeNewStrategyFragment.strategy_model.getQuestionsList().size());
                recyclerViewTopicFinish = newStrategy4stepFragmentBinding.recyclerViewTopicFinish;
                recyclerViewTopicFinish.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerViewTopicFinish.setHasFixedSize(true);
                topicFinishAdapter = new TopicFinish_Adapter(HomeNewStrategyFragment.strategy_model.getTopicsList(), getActivity());
                recyclerViewTopicFinish.setAdapter(topicFinishAdapter);
                break;
        }

    }

    // CAMBIAR ADAPTADOR DE TEMAS EN DEPENDENCIA DEL NOMBRE DE LA ASIGNATURA
    public void changeTopicAdapter(String subjectName) {

        HomeNewStrategyFragment.topicList.clear();

        Call<List<Topic>> callTopicList = topic_sevice.getTopicsBySubjectName("Bearer " + UserHelper.getToken(getContext()), subjectName);
        callTopicList.enqueue(new Callback<List<Topic>>() {
            @Override
            public void onResponse(Call<List<Topic>> call, Response<List<Topic>> response) {
                if (response.isSuccessful()) {
                    HomeNewStrategyFragment.topicList = (ArrayList<Topic>) response.body();
                    topic_adapter = new Topic_Adapter(HomeNewStrategyFragment.topicList, getContext());
                    recyclerViewTopics.setAdapter(topic_adapter);
                } else if (response.code() == 403) {
                    UserHelper.renovateToken(getContext());
                } else
                    Toast.makeText(getContext(), "ERROR", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Topic>> call, Throwable t) {
                Snackbar.make(newStrategy2stepFragmentBinding.getRoot(), getResources().getString(R.string.no_connection), Snackbar.LENGTH_INDEFINITE).setAction("Ok", v -> {
                    Toast.makeText(getContext(), getResources().getString(R.string.check_connection), Toast.LENGTH_SHORT).show();
                }).show();
            }
        });

    }

    // CAMBIAR ADAPTADOR DE GRUPOS EN DEPENDENCIA DEL NOMBRE DE LA ASIGNATURA
    public void changeGroupAdapter(String subjectName) {

        HomeNewStrategyFragment.groupList.clear();

        Call<List<Group>> callGroupList = group_service.getGroupsBySubjectNameAndTeacherId("Bearer " + UserHelper.getToken(getContext())
                , subjectName, HomeNewStrategyFragment.strategy_model.getId_teacher());
        callGroupList.enqueue(new Callback<List<Group>>() {
            @Override
            public void onResponse(Call<List<Group>> call, Response<List<Group>> response) {
                if (response.isSuccessful()) {
                    HomeNewStrategyFragment.groupList = (ArrayList<Group>) response.body();
                    group_adapter = new Group_Adapter(HomeNewStrategyFragment.groupList, getContext());
                    recyclerViewGroups.setAdapter(group_adapter);
                } else if (response.code() == 403) {
                    UserHelper.renovateToken(getContext());
                } else
                    Toast.makeText(getContext(), "ERROR", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Group>> call, Throwable t) {

            }
        });

    }

    // OBTENER PREGUNTAS DISPONIBLES EN DEPENDENCIA DEL NOMBRE DE LA ASIGNATURA
    public void getQuestionBySubjectName(String subjectName) {
        HomeNewStrategyFragment.topicList.clear();
        Call<List<Question>> call = question_service.getQuestionsBySubjectName("Bearer " + UserHelper.getToken(getContext()), subjectName);
        call.enqueue(new Callback<List<Question>>() {
            @Override
            public void onResponse(Call<List<Question>> call, Response<List<Question>> response) {
                if (response.isSuccessful()) {
                    HomeNewStrategyFragment.questionList = (ArrayList<Question>) response.body();
                } else if (response.code() == 403) {
                    UserHelper.renovateToken(getContext());
                } else
                    Toast.makeText(getContext(), "ERROR", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Question>> call, Throwable t) {

            }
        });

    }

    // AÑADIR LISTA DE TEMAS Y GRUPOS SELECCIONADOS A LA ESTRATEGIA
    public void addTopicsAndGroups() {
        HomeNewStrategyFragment.strategy_model.getTopicsList().clear();
        HomeNewStrategyFragment.strategy_model.getGroupsList().clear();
        for (Topic topic : topic_adapter.topics) {
            if (topic.isSelected())
                HomeNewStrategyFragment.strategy_model.getTopicsList().add(topic);
        }
        for (Group group : group_adapter.groups) {
            if (group.isSelected())
                HomeNewStrategyFragment.strategy_model.getGroupsList().add(group);
        }

    }

    // AÑADIR PREGUNTAS SELECIONADAS A LA ESTRATEGIA
    public void addQuestions() {
        HomeNewStrategyFragment.strategy_model.getQuestionsList().clear();
        for (Question question : questionOfStrategy_adapter.questions) {
            if (question.isSelected())
                HomeNewStrategyFragment.strategy_model.getQuestionsList().add(question);
        }
    }
}