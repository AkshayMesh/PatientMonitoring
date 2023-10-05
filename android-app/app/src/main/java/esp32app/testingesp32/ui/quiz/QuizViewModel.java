package esp32app.testingesp32.ui.quiz;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import esp32app.testingesp32.data.model.QuizModel;

public class QuizViewModel extends AndroidViewModel {
    private final MutableLiveData<ArrayList<QuizModel>> liveQuizData = new MutableLiveData<>();

    public QuizViewModel(@NonNull Application application) {
        super(application);
        setQuizLiveData();
    }

    private void setQuizLiveData() {
//        ArrayList<QuizModel> quizModels = new ArrayList<>();
        ArrayList<QuizModel> quizList = new ArrayList<>();

        // Q1
        String questionQ1 = "Q1 What does the “19” in “COVID-19” refer to?";
        ArrayList<String> optionsQ1 = new ArrayList<>();
        optionsQ1.add("A. There are 19 variants of the coronavirus.");
        optionsQ1.add("B. There are 19 symptoms of coronavirus disease.");
        optionsQ1.add("C. This is the 19th coronavirus pandemic.");
        optionsQ1.add("D. The coronavirus and the disease it causes were identified in 2019.");
        String answerQ1 = "The correct answer is D. The coronavirus and the disease it causes were identified in 2019.\nA new coronavirus, technically named SARS-CoV-2, and the disease it causes were first identified in 2019.";
        quizList.add(new QuizModel(answerQ1, questionQ1, optionsQ1, 4));

        // Q2
        String questionQ2 = "Q2 True or false: There is more than one coronavirus.";
        ArrayList<String> optionsQ2 = new ArrayList<>();
        optionsQ2.add("A. True.");
        optionsQ2.add("B. False.");
        String answerQ2 = "The correct answer is A. True.\nThere are hundreds of coronaviruses, and some of them cause disease in humans, such as mild colds and more serious illnesses or death. The SARS-CoV-2 coronavirus, along with those that cause SARS and MERS are examples of coronaviruses that can cause serious illness in people.";
        quizList.add(new QuizModel(answerQ2, questionQ2, optionsQ2, 1));

        // Q3
        String questionQ3 = "The “corona” in coronavirus means:";
        ArrayList<String> optionsQ3 = new ArrayList<>();
        optionsQ3.add("A. Sun.");
        optionsQ3.add("B. Beer.");
        optionsQ3.add("C. Strong.");
        optionsQ3.add("D. Crown.");
        String answerQ3 = "The correct answer is D. Crown.\nCrown. Spike proteins on these viruses’ outer coating give them the appearance of a crown or halo.";
        quizList.add(new QuizModel(answerQ3, questionQ3, optionsQ3, 4));

        // Q4
        String questionQ4 = "An effective hand sanitizer contains at least this percentage of alcohol to kill the coronavirus and other germs:";
        ArrayList<String> optionsQ4 = new ArrayList<>();
        optionsQ4.add("A. 30%.");
        optionsQ4.add("B. 45%.");
        optionsQ4.add("C. 60%.");
        optionsQ4.add("D. 80%.");
        String answerQ4 = "The correct answer is C. 60%.\n60%. Lower alcohol content may make a hand sanitizer less effective in killing the coronavirus and other pathogens.";
        quizList.add(new QuizModel(answerQ4, questionQ4, optionsQ4, 3));

        // Q5
        String questionQ5 = "True or false: Once you are fully vaccinated you may not be required to wear a mask in public places.";
        ArrayList<String> optionsQ5 = new ArrayList<>();
        optionsQ5.add("A. True.");
        optionsQ5.add("B. False.");
        String answerQ5 = "The correct answer is A. True.\nTrue. The CDC has updated its recommendations on resuming some activities once you have completed your COVID-19 vaccines and allowed at least two weeks for your immune system to respond after your last shot. Fully vaccinated people can resume activities without wearing a mask or physically distancing, except where required by federal, state, local, tribal, or territorial laws, rules, and regulations, including local business and workplace guidance. The CDC continues to recommend that masks and physical distancing are required when going to the doctor’s office, hospitals or long-term care facilities, including all Johns Hopkins hospitals, care centers and offices.";
        quizList.add(new QuizModel(answerQ5, questionQ5, optionsQ5, 1));

        // Q6
        String questionQ6 = "When is it safe to be closer than 6 feet to another person who is not sick with COVID-19?";
        ArrayList<String> optionsQ6 = new ArrayList<>();
        optionsQ6.add("A. When the person is related to you.");
        optionsQ6.add("B. When you have already had COVID-19.");
        optionsQ6.add("C. When the person normally lives with you.");
        optionsQ6.add("D. When you are outdoors.");
        String answerQ6 = "The correct answer is C. When the person normally lives with you.\nWhen the person normally lives with you in your home. When you’re around anyone from outside your regular household, physical distancing and mask-wearing are essential to avoid spreading the coronavirus. Outdoors is generally safer than inside, but you should still maintain physical distance and wear your face mask.";
        quizList.add(new QuizModel(answerQ6, questionQ6, optionsQ6, 3));

// Q7
        String questionQ7 = "Which of these is not a common COVID-19 symptom?";
        ArrayList<String> optionsQ7 = new ArrayList<>();
        optionsQ7.add("A. Blurred vision.");
        optionsQ7.add("B. A cough.");
        optionsQ7.add("C. Unusual fatigue.");
        optionsQ7.add("D. Fever.");
        optionsQ7.add("E. Inability to taste or smell.");
        String answerQ7 = "The correct answer is A. Blurred vision.\nBlurred vision. The others are common things experienced by people who have COVID-19.";
        quizList.add(new QuizModel(answerQ7, questionQ7, optionsQ7, 1));

// Q8
        String questionQ8 = "Staying apart from other people when you have been exposed to the coronavirus is called:";
        ArrayList<String> optionsQ8 = new ArrayList<>();
        optionsQ8.add("A. Physical distancing.");
        optionsQ8.add("B. Isolation.");
        optionsQ8.add("C. Quarantine.");
        String answerQ8 = "The correct answer is C. Quarantine.\nA person who has been exposed to an infectious illness might be under quarantine to wait and see if they will become sick. Physical distancing refers to staying at least 6 feet apart from someone to avoid spreading disease. Isolation is keeping someone sick with COVID-19 apart from other patients in a health care setting.";
        quizList.add(new QuizModel(answerQ8, questionQ8, optionsQ8, 3));

// Q9
        String questionQ9 = "What is the safest way to celebrate your birthday during the COVID-19 pandemic?";
        ArrayList<String> optionsQ9 = new ArrayList<>();
        optionsQ9.add("A. Sharing a meal with relatives only.");
        optionsQ9.add("B. Having a virtual party using an online conferencing app.");
        optionsQ9.add("C. Hitting your favorite bar when it’s less crowded.");
        optionsQ9.add("D. Gathering at a friend’s house with people you know well.");
        String answerQ9 = "The correct answer is B. Having a virtual party using an online conferencing app.\nHaving a virtual party is the safest option. Gathering in person, especially indoors, with people that you don’t normally live with — even if they’re relatives or good friends — is risky. Situations where masks aren’t being worn or are being removed to eat or drink are especially likely to spread the coronavirus and cause illness.";
        quizList.add(new QuizModel(answerQ9, questionQ9, optionsQ9, 2));

// Q10
        String questionQ10 = "True or false: Having a food allergy means you should not get a COVID-19 vaccine.";
        ArrayList<String> optionsQ10 = new ArrayList<>();
        optionsQ10.add("A. True.");
        optionsQ10.add("B. False.");
        String answerQ10 = "The correct answer is B. False.\nFalse. People with food allergies can be vaccinated for the coronavirus. However, those who are seriously allergic to any of the ingredients in the vaccine itself should not get the vaccine.";
        quizList.add(new QuizModel(answerQ10, questionQ10, optionsQ10, 2));

// Q11
        String questionQ11 = "What type of respiratory protection does the CDC recommend for healthcare workers caring for COVID-19 patients?";
        ArrayList<String> optionsQ11 = new ArrayList<>();
        optionsQ11.add("A. Cloth masks.");
        optionsQ11.add("B. N95 respirators.");
        optionsQ11.add("C. Surgical masks.");
        optionsQ11.add("D. Bandanas.");
        String answerQ11 = "The correct answer is B. N95 respirators.\nThe CDC recommends healthcare workers caring for COVID-19 patients to use N95 respirators, as they provide a higher level of respiratory protection compared to cloth masks, surgical masks, or bandanas.";
        quizList.add(new QuizModel(answerQ11, questionQ11, optionsQ11, 2));

// Q12
        String questionQ12 = "How long should you wash your hands to effectively remove germs?";
        ArrayList<String> optionsQ12 = new ArrayList<>();
        optionsQ12.add("A. 5 seconds.");
        optionsQ12.add("B. 15 seconds.");
        optionsQ12.add("C. 30 seconds.");
        optionsQ12.add("D. 1 minute.");
        String answerQ12 = "The correct answer is C. 30 seconds.\nTo effectively remove germs, it is recommended to wash your hands for at least 20 seconds to 30 seconds with soap and water.";
        quizList.add(new QuizModel(answerQ12, questionQ12, optionsQ12, 3));

// Q13
        String questionQ13 = "What is the primary mode of transmission for COVID-19?";
        ArrayList<String> optionsQ13 = new ArrayList<>();
        optionsQ13.add("A. Airborne transmission.");
        optionsQ13.add("B. Vector-borne transmission.");
        optionsQ13.add("C. Fecal-oral transmission.");
        optionsQ13.add("D. Respiratory droplet transmission.");
        String answerQ13 = "The correct answer is D. Respiratory droplet transmission.\nThe primary mode of transmission for COVID-19 is through respiratory droplets released when an infected person coughs, sneezes, talks, or breathes. It is important to wear masks and practice physical distancing to prevent the spread.";
        quizList.add(new QuizModel(answerQ13, questionQ13, optionsQ13, 4));

// Q14
        String questionQ14 = "What is the recommended distance for physical distancing to reduce the spread of COVID-19?";
        ArrayList<String> optionsQ14 = new ArrayList<>();
        optionsQ14.add("A. 1 foot.");
        optionsQ14.add("B. 3 feet.");
        optionsQ14.add("C. 6 feet.");
        optionsQ14.add("D. 10 feet.");
        String answerQ14 = "The correct answer is C. 6 feet.\nThe recommended distance for physical distancing to reduce the spread of COVID-19 is at least 6 feet from individuals who are not in your household. This helps minimize the risk of exposure to respiratory droplets.";
        quizList.add(new QuizModel(answerQ14, questionQ14, optionsQ14, 3));

// Q15
        String questionQ15 = "Which of the following is a common symptom of COVID-19?";
        ArrayList<String> optionsQ15 = new ArrayList<>();
        optionsQ15.add("A. Green fingers.");
        optionsQ15.add("B. Rainbow-colored hair.");
        optionsQ15.add("C. Loss of taste or smell.");
        optionsQ15.add("D. Glowing skin.");
        String answerQ15 = "The correct answer is C. Loss of taste or smell.\nLoss of taste or smell (anosmia) is a common symptom of COVID-19. It's important to be aware of this symptom, along with other common symptoms such as fever, cough, and difficulty breathing.";
        quizList.add(new QuizModel(answerQ15, questionQ15, optionsQ15, 3));


        liveQuizData.setValue(quizList);
    }

    public LiveData<ArrayList<QuizModel>> getLiveQuizData() {
        return liveQuizData;
    }

}
