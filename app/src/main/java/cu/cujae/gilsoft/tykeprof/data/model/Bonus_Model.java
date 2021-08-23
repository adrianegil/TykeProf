package cu.cujae.gilsoft.tykeprof.data.model;

import com.google.gson.annotations.SerializedName;

public class Bonus_Model {

    @SerializedName("bonificacion")
    private Integer bonus;

    @SerializedName("tiempoMenor")
    private Long time_less;

    public Bonus_Model(Integer bonus, Long time_less) {
        this.bonus = bonus;
        this.time_less = time_less;
    }

    public Integer getBonus() {
        return bonus;
    }

    public void setBonus(Integer bonus) {
        this.bonus = bonus;
    }

    public Long getTime_less() {
        return time_less;
    }

    public void setTime_less(Long time_less) {
        this.time_less = time_less;
    }
}
