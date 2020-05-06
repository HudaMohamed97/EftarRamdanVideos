import com.google.gson.annotations.SerializedName
import com.huda.eftarramdanvideos.Models.QuestionModel


data class SingelQuestionData(

    @SerializedName("data") val data: QuestionModel
)