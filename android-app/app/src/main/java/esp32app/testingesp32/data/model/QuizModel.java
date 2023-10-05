package esp32app.testingesp32.data.model;

import java.util.ArrayList;

public class QuizModel {
    public String answer;
    public String question;
    public int correctIndex;
    public ArrayList<String> options;
    public String tempAnswer;

    public QuizModel(String answer, String question, ArrayList<String> options, int correctIndex) {
        this.answer = answer;
        this.question = question;
        this.options = options;
        this.correctIndex = correctIndex;
    }

    public QuizModel() {
    }
}
