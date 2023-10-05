package esp32app.testingesp32.ui.faq;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import esp32app.testingesp32.data.model.FaqModel;

public class FAQViewModel extends AndroidViewModel {

    private final MutableLiveData<ArrayList<FaqModel>> liveFaqData = new MutableLiveData<>();

    public FAQViewModel(@NonNull Application application) {
        super(application);
        setFaqLiveData();
    }

    private void setFaqLiveData() {
        ArrayList<FaqModel> faqModels = new ArrayList<>();

        FaqModel model1 = new FaqModel();
        model1.question = "How we can manage our emotions?";
        model1.answer = "\uF0B7 Try to keep your temper - access the information you hear reasonably and calmly.\n" +
                "\uF0B7 I stay at home but I can stay in touch with loved ones - with the help of technology we can " +
                "communicate with relatives and friends daily by phone, skype, viber etc.\n" +
                "\uF0B7 Remember that you are not alone - the coronavirus pandemic concerns us all, we all experience similar " +
                "feelings of anxiety and fear.\n" +
                "\uF0B7 Contact a health professional if you feel it is very difficult to manage the situation yourself";

        FaqModel model2 = new FaqModel();
        model2.question = "As the world is reopening, I’m anxious. What should I do?";
        model2.answer = "\uF0B7 Balance your exposure to the media. It's important to stay informed about current events. ...\n" +
                "\uF0B7 Focus on things you can control. It's worth thinking about whether the things that are making you " +
                "anxious are things you can control. ...\n" +
                "\uF0B7 Managing your feelings. ...\n" +
                "\uF0B7 Enjoying the here and now.";

        FaqModel model3 = new FaqModel();
        model3.question = "How do you overcome anxiety during the pandemic?";
        model3.answer = "Even a walk around your neighbourhood can make you feel better. Just be sure to avoid crowds, keep your " +
                "distance from people you encounter, and obey restrictions in your area. Find ways to exercise. Staying active " +
                "will help you release anxiety, relieve stress, and manage your mood";

        FaqModel model4 = new FaqModel();
        model4.question = "How to deal with stress and anxiety?";
        model4.answer = "Here are some healthy ways you can deal with stress:\n" +
                "1. Take breaks from watching, reading, or listening to news stories, including those on social media. \n" +
                "2. Take care of yourself. \n" +
                "3. Take care of your body. \n" +
                "4. Make time to unwind. \n" +
                "5. Talk to others. \n" +
                "6. Connect with your community- or faith-based organizations.\n" +
                "7. Avoid drugs and alcohol.";

        FaqModel model5 = new FaqModel();
        model5.question = "Q5. Is agoraphobia a mental illness?";
        model5.answer = "Agoraphobia is a type of anxiety disorder. A person with agoraphobia is afraid to leave environments\n" +
                "they know and consider to be safe for fear of having anxiety or a panic attack. Agoraphobia responds well to\ntreatment.";

        FaqModel model6 = new FaqModel();
        model6.question = "Q6. How to calm your mind?";
        model6.answer = "Here are some simple exercises you can try that might calm you down.\n" +
                "1. Take a break. Focus on your breathing. Listen to music.\n" +
                "2. Spend some time in nature. Try active relaxation. Think of somewhere else.\n" +
                "3. Try guided meditation. Get creative.";

        FaqModel model7 = new FaqModel();
        model7.question = "Q7. How do I stop overthinking and stress?";
        model7.answer = "\uF0B7 Awareness is the beginning of change.\n" +
                "\uF0B7 Don't think of what can go wrong, but what can go right.\n" +
                "\uF0B7 Distract yourself into happiness.\n" +
                "\uF0B7 Put things into perspective.\n" +
                "\uF0B7 Stop waiting for perfection.\n" +
                "\uF0B7 Change your view of fear.\n" +
                "\uF0B7 Put a timer to work.";

        FaqModel model8 = new FaqModel();
        model8.question = "Q8. Why won’t my anxiety go away?";
        model8.answer = "\uF0B7 An anxiety disorder can be caused by multiple factors, such as genetics, environmental stressors and medical\n" +
                "conditions. New research also indicates that chronic anxiety symptoms that will not go away can be due to an\n" +
                "autoimmune response, triggered by common infections.";

        FaqModel model9 = new FaqModel();
        model9.question = "Q9. What foods get rid of anxiety?";
        model9.answer = "\uF0B7 Foods rich in zinc such as oysters, cashews, liver, beef, and egg yolks have been linked to lowered anxiety.\n" +
                "Other foods, including fatty fish like wild Alaskan salmon, contain omega-3 fatty acids. A study completed on\n" +
                "medical students in 2011 was one of the first to show that omega-3s may help reduce anxiety.";

        FaqModel model10 = new FaqModel();
        model10.question = "Q10. How do you handle all the suffering in the world?";
        model10.answer = "\uF0B7 It's not your job to take on the world's pain.\n" +
                "\uF0B7 Practice deep breathing to exhale stress.\n" +
                "\uF0B7 Limit exposure to news.\n" +
                "\uF0B7 Do not let others feed your panic.\n" +
                "\uF0B7 If you notice yourself absorbing the stress or pain of others, take some alone time to regroup and replenish\n" +
                "yourself.\n" +
                "\uF0B7 Do not get into victim mode.\n" +
                "\uF0B7 Stay in the now.";

        FaqModel model11 = new FaqModel();
        model11.question = "Q11. How do bad moods differ from depression?";
        model11.answer = "\uF0B7 Depression can come up for no reason, and it lasts for a long time. It's much more than sadness or low mood.\n" +
                "People who experience depression may feel worthless or hopeless. They may feel unreasonable guilty.";

        FaqModel model12 = new FaqModel();
        model12.question = "Q12. What feeling is most likely to be a symptom of depression?";
        model12.answer = "\uF0B7 Depression (major depressive disorder) is a common and serious medical illness that negatively affects how you feel,\n" +
                "the way you think and how you act. Fortunately, it is also treatable. Depression causes feelings of sadness and/or a loss\n" +
                "of interest in activities you once enjoyed.";

        FaqModel model13 = new FaqModel();
        model13.question = "Q13. When do people start having depression?";
        model13.answer = "\uF0B7 Depression often begins in the teens, 20s or 30s, but it can happen at any age. More women than men are diagnosed\n" +
                "with depression, but this may be due in part because women are more likely to seek treatment.";

        FaqModel model14 = new FaqModel();
        model14.question = "Q14. What happens if you don't treat depression?";
        model14.answer = "\uF0B7 According to the Mayo Clinic, patients with untreated long-term depression are more prone to sleep disruptions, heart\n" +
                "disease, weight gain or loss, weakened immune systems, and physical pain.";

        FaqModel model15 = new FaqModel();
        model15.question = "Q15. What is the importance of meditation?";
        model15.answer = "\uF0B7 Meditation originally was meant to help deepen understanding of the sacred and mystical forces of life. These\ndays, meditation is commonly used for relaxation and stress reduction. Meditation is considered a type of mind-body\n" +
                "complementary medicine. Meditation can produce a deep state of relaxation and a tranquil mind.";

        FaqModel model16 = new FaqModel();
        model16.question = "Q16. Which meditation is good for brain?";
        model16.answer = "\uF0B7 While research on mindfulness meditation is still in the early stages, some small, initial studies have found that over\ntime mindfulness meditation may lead to increases in gray matter density in the hippocampus and other frontal\n" +
                "regions of the brain as well as increases in anterior insula and cortical thickness.";

        FaqModel model17 = new FaqModel();
        model17.question = "Q17. Which meditation is so powerful?";
        model17.answer = "\uF0B7 Yoga Nidra, or yogic sleep is a well-known and immensely powerful meditation technique to promote deep rest and\n" +
                "relaxation.";

        FaqModel model18 = new FaqModel();
        model18.question = "Q18. What are the 10 benefits of meditation?";
        model18.answer = "\uF0B7 Lowers stress. Perhaps the most common reason why people practice meditation is to lower the level of stress.\n" +
                "\uF0B7 Reduces anxiety.\n" +
                "\uF0B7 Enhances mental health.\n" +
                "\uF0B7 Improve self-awareness.\n" +
                "\uF0B7 Increases concentration and attention span.\n" +
                "\uF0B7 Reduce memory loss.\n" +
                "\uF0B7 Generates empathy and kindness.\n" +
                "\uF0B7 Improves sleep hygiene.";

        FaqModel model19 = new FaqModel();
        model19.question = "Q19. How do I know I need a therapist?";
        model19.answer = "\uF0B7 Here are the signs it might be time for therapy:\n" +
                "\uF0B7 You're super overwhelmed.\n" +
                "\uF0B7 You're sleeping too much or too little.\n" +
                "\uF0B7 You're avoiding being social or can't keep relationships.\n" +
                "\uF0B7 Your anxious thoughts consume you.\n" +
                "\uF0B7 You can't control your emotions.\n" +
                "\uF0B7 You don't care about anything.\n" +
                "\uF0B7 You feel hopeless.\n" +
                "\uF0B7 You're having problems at work.";

        FaqModel model20 = new FaqModel();
        model20.question = "Q20. What is the difference between a therapist and a psychologist?";
        model20.answer = "\uF0B7 Therapists typically use a more holistic approach. This means discussing your whole person, allowing you to\n" +
                "focus on your emotional state. Psychologists may focus more on how thoughts and behaviors interact with your\n" +
                "environment.";

        FaqModel model21 = new FaqModel();
        model21.question = "Q21. What to do if you are struggling to sleep?";
        model21.answer = "\uF0B7 How you can treat insomnia yourself:\n" +
                "1. Go to bed and wake up at the same time every day.\n" +
                "2. Relax at least 1 hour before bed, for example, take a bath or read a book.\n" +
                "3. Make sure your bedroom is dark and quiet – use curtains, blinds, an eye mask or earplugs if needed.\n" +
                "4. Exercise regularly during the day.";

        FaqModel model22 = new FaqModel();
        model22.question = "Q22. Why am I having difficulty sleeping?";
        model22.answer = "\uF0B7 Stress and anxiety. A poor sleeping environment – such as an uncomfortable bed, or a bedroom that's too light, noisy,\n" +
                "hot or cold. Lifestyle factors – such as jet lag, shift work, or drinking alcohol or caffeine before going to bed. Mental\n" +
                "health conditions – such as depression and schizophrenia.";

        FaqModel model23 = new FaqModel();
        model23.question = "Q23. Why do I struggle to stay motivated at work?";
        model23.answer = "\uF0B7 This may be caused by experiencing burnout, working in a toxic environment, or feeling you have no control over your\n" +
                "role. Working in short bursts is one of the best strategies to get you over times of low motivation.";

        FaqModel model24 = new FaqModel();
        model24.question = "Q24: Are elderly people at increased risk for COVID-19 infection?";
        model24.answer = "\uF0B7 Based on early reports from China, where COVID-19 first started, shows that older adults are at a higher risk of\n" +
                "getting very sick from this illness. COVID-19's case fatality rate increases with age, according to China's data.";

        FaqModel model25 = new FaqModel();
        model25.question = "Q25: Is it safe for me/my elderly member to travel?";
        model25.answer = "\uF0B7 We strongly recommend elderly people not to travel in the US or internationally at this time. The CDC\n" +
                "recommends that people at increased risk for COVID-19 avoid traveling by airplane and on cruise ships.";

        FaqModel model26 = new FaqModel();
        model26.question = "Q26. Where can I get more information about COVID-19?";
        model26.answer = "\uF0B7 To get more information about COVID-19, please visit the following websites:\n" +
                "World Health Organization - Coronavirus Disease (COVID-19) Outbreak\n" +
                "Centers for Disease Control and Prevention - Coronavirus (COVID-19)\n" +
                "Florida Health - 2019 Novel Coronavirus Response (COVID-19)";
        FaqModel model27 = new FaqModel();
        model27.question = "Q27. What are the benefits of regular exercise?";
        model27.answer = "\uF0B7 Benefits of regular exercise:\n" +
                "1. Improved cardiovascular health.\n" +
                "2. Increased strength and muscle tone.\n" +
                "3. Enhanced mood and mental well-being.\n" +
                "4. Weight management.\n" +
                "5. Better sleep quality.\n" +
                "6. Reduced risk of chronic diseases.";

        faqModels.add(model1);
        faqModels.add(model2);
        faqModels.add(model3);
        faqModels.add(model4);
        faqModels.add(model5);
        faqModels.add(model6);
        faqModels.add(model7);
        faqModels.add(model8);
        faqModels.add(model9);
        faqModels.add(model10);
        faqModels.add(model11);
        faqModels.add(model12);
        faqModels.add(model13);
        faqModels.add(model14);
        faqModels.add(model15);
        faqModels.add(model16);
        faqModels.add(model17);
        faqModels.add(model18);
        faqModels.add(model19);
        faqModels.add(model20);
        faqModels.add(model21);
        faqModels.add(model22);
        faqModels.add(model23);
        faqModels.add(model24);
        faqModels.add(model25);
        faqModels.add(model26);
        faqModels.add(model27);

        liveFaqData.setValue(faqModels);
    }

    public LiveData<ArrayList<FaqModel>> getLiveFaqData() {
        return liveFaqData;
    }
}
