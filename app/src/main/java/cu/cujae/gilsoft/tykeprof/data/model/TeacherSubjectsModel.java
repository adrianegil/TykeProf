package cu.cujae.gilsoft.tykeprof.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TeacherSubjectsModel {

        @SerializedName("idProfesor")
        private Integer id_teacher;
        @SerializedName("asignaturas")
        private List<String> subjects;

        public TeacherSubjectsModel() {
        }

        public Integer getId_teacher() {
                return id_teacher;
        }

        public void setId_teacher(Integer id_teacher) {
                this.id_teacher = id_teacher;
        }

        public List<String> getSubjects() {
                return subjects;
        }

        public void setSubjects(List<String> subjects) {
                this.subjects = subjects;
        }
}
