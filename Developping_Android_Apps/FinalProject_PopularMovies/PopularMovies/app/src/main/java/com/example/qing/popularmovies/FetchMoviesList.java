package com.example.qing.popularmovies;

import android.os.AsyncTask;
import android.util.Log;
import android.util.LogPrinter;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


class FetchList extends AsyncTask<String, Void, Object[]> {

    final static String LOG_TAG  = FetchList.class.getSimpleName();
    final static String fakeJson = "{\"page\":1,\"results\":[{\"poster_path\":\"\\/9HE9xiNMEFJnCzndlkWD7oPfAOx.jpg\",\"adult\":false,\"overview\":\"In 1926, Newt Scamander arrives at the Magical Congress of the United States of America with a magically expanded briefcase, which houses a number of dangerous creatures and their habitats. When the creatures escape from the briefcase, it sends the American wizarding authorities after Newt, and threatens to strain even further the state of magical and non-magical relations.\",\"release_date\":\"2016-11-16\",\"genre_ids\":[12,10751,14],\"id\":259316,\"original_title\":\"Fantastic Beasts and Where to Find Them\",\"original_language\":\"en\",\"title\":\"Fantastic Beasts and Where to Find Them\",\"backdrop_path\":\"\\/6I2tPx6KIiBB4TWFiWwNUzrbxUn.jpg\",\"popularity\":42.902065,\"vote_count\":435,\"video\":false,\"vote_average\":7.38},{\"poster_path\":\"\\/xfWac8MTYDxujaxgPVcRD9yZaul.jpg\",\"adult\":false,\"overview\":\"After his career is destroyed, a brilliant but arrogant surgeon gets a new lease on life when a sorcerer takes him under his wing and trains him to defend the world against evil.\",\"release_date\":\"2016-10-25\",\"genre_ids\":[28,12,14,878],\"id\":284052,\"original_title\":\"Doctor Strange\",\"original_language\":\"en\",\"title\":\"Doctor Strange\",\"backdrop_path\":\"\\/tFI8VLMgSTTU38i8TIsklfqS9Nl.jpg\",\"popularity\":40.131582,\"vote_count\":1080,\"video\":false,\"vote_average\":6.63},{\"poster_path\":\"\\/4Iu5f2nv7huqvuYkmZvSPOtbFjs.jpg\",\"adult\":false,\"overview\":\"Taking place after alien crafts land around the world, an expert linguist is recruited by the military to determine whether they come in peace or are a threat.\",\"release_date\":\"2016-11-10\",\"genre_ids\":[18,878],\"id\":329865,\"original_title\":\"Arrival\",\"original_language\":\"en\",\"title\":\"Arrival\",\"backdrop_path\":\"\\/wFFlaVHmQG4U43m0l3eQqHZluvn.jpg\",\"popularity\":26.863177,\"vote_count\":294,\"video\":false,\"vote_average\":6.67},{\"poster_path\":\"\\/kqjL17yufvn9OVLyXYpvtyrFfak.jpg\",\"adult\":false,\"overview\":\"An apocalyptic story set in the furthest reaches of our planet, in a stark desert landscape where humanity is broken, and most everyone is crazed fighting for the necessities of life. Within this world exist two rebels on the run who just might be able to restore order. There's Max, a man of action and a man of few words, who seeks peace of mind following the loss of his wife and child in the aftermath of the chaos. And Furiosa, a woman of action and a woman who believes her path to survival may be achieved if she can make it across the desert back to her childhood homeland.\",\"release_date\":\"2015-05-13\",\"genre_ids\":[28,12,878,53],\"id\":76341,\"original_title\":\"Mad Max: Fury Road\",\"original_language\":\"en\",\"title\":\"Mad Max: Fury Road\",\"backdrop_path\":\"\\/tbhdm8UJAb4ViCTsulYFL3lxMCd.jpg\",\"popularity\":23.940018,\"vote_count\":5765,\"video\":false,\"vote_average\":7.18},{\"poster_path\":\"\\/lFSSLTlFozwpaGlO31OoUeirBgQ.jpg\",\"adult\":false,\"overview\":\"The most dangerous former operative of the CIA is drawn out of hiding to uncover hidden truths about his past.\",\"release_date\":\"2016-07-27\",\"genre_ids\":[28,53],\"id\":324668,\"original_title\":\"Jason Bourne\",\"original_language\":\"en\",\"title\":\"Jason Bourne\",\"backdrop_path\":\"\\/AoT2YrJUJlg5vKE3iMOLvHlTd3m.jpg\",\"popularity\":23.523337,\"vote_count\":1088,\"video\":false,\"vote_average\":5.55},{\"poster_path\":\"\\/e1mjopzAS2KNsvpbpahQ1a6SkSn.jpg\",\"adult\":false,\"overview\":\"From DC Comics comes the Suicide Squad, an antihero team of incarcerated supervillains who act as deniable assets for the United States government, undertaking high-risk black ops missions in exchange for commuted prison sentences.\",\"release_date\":\"2016-08-02\",\"genre_ids\":[28,80,14],\"id\":297761,\"original_title\":\"Suicide Squad\",\"original_language\":\"en\",\"title\":\"Suicide Squad\",\"backdrop_path\":\"\\/ndlQ2Cuc3cjTL7lTynw6I4boP4S.jpg\",\"popularity\":22.50975,\"vote_count\":2617,\"video\":false,\"vote_average\":5.95},{\"poster_path\":\"\\/jjBgi2r5cRt36xF6iNUEhzscEcb.jpg\",\"adult\":false,\"overview\":\"Twenty-two years after the events of Jurassic Park, Isla Nublar now features a fully functioning dinosaur theme park, Jurassic World, as originally envisioned by John Hammond.\",\"release_date\":\"2015-06-09\",\"genre_ids\":[28,12,878,53],\"id\":135397,\"original_title\":\"Jurassic World\",\"original_language\":\"en\",\"title\":\"Jurassic World\",\"backdrop_path\":\"\\/dkMD5qlogeRMiEixC4YNPUvax2T.jpg\",\"popularity\":17.869035,\"vote_count\":5277,\"video\":false,\"vote_average\":6.57},{\"poster_path\":\"\\/5N20rQURev5CNDcMjHVUZhpoCNC.jpg\",\"adult\":false,\"overview\":\"Following the events of Age of Ultron, the collective governments of the world pass an act designed to regulate all superhuman activity. This polarizes opinion amongst the Avengers, causing two factions to side with Iron Man or Captain America, which causes an epic battle between former allies.\",\"release_date\":\"2016-04-27\",\"genre_ids\":[28,878,53],\"id\":271110,\"original_title\":\"Captain America: Civil War\",\"original_language\":\"en\",\"title\":\"Captain America: Civil War\",\"backdrop_path\":\"\\/m5O3SZvQ6EgD5XXXLPIP1wLppeW.jpg\",\"popularity\":17.072926,\"vote_count\":3535,\"video\":false,\"vote_average\":6.78},{\"poster_path\":\"\\/5JU9ytZJyR3zmClGmVm9q4Geqbd.jpg\",\"adult\":false,\"overview\":\"The year is 2029. John Connor, leader of the resistance continues the war against the machines. At the Los Angeles offensive, John's fears of the unknown future begin to emerge when TECOM spies reveal a new plot by SkyNet that will attack him from both fronts; past and future, and will ultimately change warfare forever.\",\"release_date\":\"2015-06-23\",\"genre_ids\":[878,28,53,12],\"id\":87101,\"original_title\":\"Terminator Genisys\",\"original_language\":\"en\",\"title\":\"Terminator Genisys\",\"backdrop_path\":\"\\/bIlYH4l2AyYvEysmS2AOfjO7Dn8.jpg\",\"popularity\":17.013626,\"vote_count\":2476,\"video\":false,\"vote_average\":5.87},{\"poster_path\":\"\\/z09QAf8WbZncbitewNk6lKYMZsh.jpg\",\"adult\":false,\"overview\":\"\\\"Finding Dory\\\" reunites Dory with friends Nemo and Marlin on a search for answers about her past. What can she remember? Who are her parents? And where did she learn to speak Whale?\",\"release_date\":\"2016-06-16\",\"genre_ids\":[16,10751],\"id\":127380,\"original_title\":\"Finding Dory\",\"original_language\":\"en\",\"title\":\"Finding Dory\",\"backdrop_path\":\"\\/iWRKYHTFlsrxQtfQqFOQyceL83P.jpg\",\"popularity\":15.688398,\"vote_count\":1405,\"video\":false,\"vote_average\":6.67},{\"poster_path\":\"\\/tgfRDJs5PFW20Aoh1orEzuxW8cN.jpg\",\"adult\":false,\"overview\":\"Arthur Bishop thought he had put his murderous past behind him when his most formidable foe kidnaps the love of his life. Now he is forced to travel the globe to complete three impossible assassinations, and do what he does best, make them look like accidents.\",\"release_date\":\"2016-08-25\",\"genre_ids\":[28,80,53],\"id\":278924,\"original_title\":\"Mechanic: Resurrection\",\"original_language\":\"en\",\"title\":\"Mechanic: Resurrection\",\"backdrop_path\":\"\\/6kMu4vECAyTpj2Z7n8viJ4RAaYh.jpg\",\"popularity\":14.998829,\"vote_count\":553,\"video\":false,\"vote_average\":4.77},{\"poster_path\":\"\\/WLQN5aiQG8wc9SeKwixW7pAR8K.jpg\",\"adult\":false,\"overview\":\"The quiet life of a terrier named Max is upended when his owner takes in Duke, a stray whom Max instantly dislikes.\",\"release_date\":\"2016-06-18\",\"genre_ids\":[12,16,35,10751],\"id\":328111,\"original_title\":\"The Secret Life of Pets\",\"original_language\":\"en\",\"title\":\"The Secret Life of Pets\",\"backdrop_path\":\"\\/lubzBMQLLmG88CLQ4F3TxZr2Q7N.jpg\",\"popularity\":14.996807,\"vote_count\":1093,\"video\":false,\"vote_average\":5.98},{\"poster_path\":\"\\/mLrQMqyZgLeP8FrT5LCobKAiqmK.jpg\",\"adult\":false,\"overview\":\"The USS Enterprise crew explores the furthest reaches of uncharted space, where they encounter a mysterious new enemy who puts them and everything the Federation stands for to the test.\",\"release_date\":\"2016-07-07\",\"genre_ids\":[28,12,878,53],\"id\":188927,\"original_title\":\"Star Trek Beyond\",\"original_language\":\"en\",\"title\":\"Star Trek Beyond\",\"backdrop_path\":\"\\/6uBlEXZCUHM15UNZqNig17VdN4m.jpg\",\"popularity\":14.99564,\"vote_count\":1161,\"video\":false,\"vote_average\":6.3},{\"poster_path\":\"\\/6w1VjTPTjTaA5oNvsAg0y4H6bou.jpg\",\"adult\":false,\"overview\":\"Beatrice Prior must confront her inner demons and continue her fight against a powerful alliance which threatens to tear her society apart.\",\"release_date\":\"2015-03-18\",\"genre_ids\":[12,878,53],\"id\":262500,\"original_title\":\"Insurgent\",\"original_language\":\"en\",\"title\":\"Insurgent\",\"backdrop_path\":\"\\/L5QRL1O3fGs2hH1LbtYyVl8Tce.jpg\",\"popularity\":14.98955,\"vote_count\":2311,\"video\":false,\"vote_average\":6.34},{\"poster_path\":\"\\/gj282Pniaa78ZJfbaixyLXnXEDI.jpg\",\"adult\":false,\"overview\":\"Katniss Everdeen reluctantly becomes the symbol of a mass rebellion against the autocratic Capitol.\",\"release_date\":\"2014-11-18\",\"genre_ids\":[878,12,53],\"id\":131631,\"original_title\":\"The Hunger Games: Mockingjay - Part 1\",\"original_language\":\"en\",\"title\":\"The Hunger Games: Mockingjay - Part 1\",\"backdrop_path\":\"\\/83nHcz2KcnEpPXY50Ky2VldewJJ.jpg\",\"popularity\":14.748561,\"vote_count\":3387,\"video\":false,\"vote_average\":6.67},{\"poster_path\":\"\\/lGHoH7pHsiwPs96tM9nA42id7K0.jpg\",\"adult\":false,\"overview\":\"The BFG is no ordinary bone-crunching giant. He is far too nice and jumbly. It's lucky for Sophie that he is. Had she been carried off in the middle of the night by the Bloodbottler, or any of the other giants—rather than the BFG—she would have soon become breakfast. When Sophie hears that the giants are flush-bunking off to England to swollomp a few nice little chiddlers, she decides she must stop them once and for all. And the BFG is going to help her!\",\"release_date\":\"2016-06-01\",\"genre_ids\":[12,10751,14],\"id\":267935,\"original_title\":\"The BFG\",\"original_language\":\"en\",\"title\":\"The BFG\",\"backdrop_path\":\"\\/eYT9XQBo1eC4DwPYqhCol0dFFc2.jpg\",\"popularity\":13.941033,\"vote_count\":285,\"video\":false,\"vote_average\":5.67},{\"poster_path\":\"\\/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg\",\"adult\":false,\"overview\":\"Interstellar chronicles the adventures of a group of explorers who make use of a newly discovered wormhole to surpass the limitations on human space travel and conquer the vast distances involved in an interstellar voyage.\",\"release_date\":\"2014-11-05\",\"genre_ids\":[12,18,878],\"id\":157336,\"original_title\":\"Interstellar\",\"original_language\":\"en\",\"title\":\"Interstellar\",\"backdrop_path\":\"\\/xu9zaAevzQ5nnrsXN6JcahLnG4i.jpg\",\"popularity\":13.910441,\"vote_count\":6060,\"video\":false,\"vote_average\":8.06},{\"poster_path\":\"\\/inVq3FRqcYIRl2la8iZikYYxFNR.jpg\",\"adult\":false,\"overview\":\"Based upon Marvel Comics’ most unconventional anti-hero, DEADPOOL tells the origin story of former Special Forces operative turned mercenary Wade Wilson, who after being subjected to a rogue experiment that leaves him with accelerated healing powers, adopts the alter ego Deadpool. Armed with his new abilities and a dark, twisted sense of humor, Deadpool hunts down the man who nearly destroyed his life.\",\"release_date\":\"2016-02-09\",\"genre_ids\":[28,12,35,10749],\"id\":293660,\"original_title\":\"Deadpool\",\"original_language\":\"en\",\"title\":\"Deadpool\",\"backdrop_path\":\"\\/n1y094tVDFATSzkTnFxoGZ1qNsG.jpg\",\"popularity\":12.730526,\"vote_count\":5501,\"video\":false,\"vote_average\":7.2},{\"poster_path\":\"\\/vgAHvS0bT3fpcpnJqT6uDTUsHTo.jpg\",\"adult\":false,\"overview\":\"Immediately after the events of The Desolation of Smaug, Bilbo and the dwarves try to defend Erebor's mountain of treasure from others who claim it: the men of the ruined Laketown and the elves of Mirkwood. Meanwhile an army of Orcs led by Azog the Defiler is marching on Erebor, fueled by the rise of the dark lord Sauron. Dwarves, elves and men must unite, and the hope for Middle-Earth falls into Bilbo's hands.\",\"release_date\":\"2014-12-10\",\"genre_ids\":[12,14],\"id\":122917,\"original_title\":\"The Hobbit: The Battle of the Five Armies\",\"original_language\":\"en\",\"title\":\"The Hobbit: The Battle of the Five Armies\",\"backdrop_path\":\"\\/qhH3GyIfAnGv1pjdV3mw03qAilg.jpg\",\"popularity\":12.625533,\"vote_count\":2975,\"video\":false,\"vote_average\":7.12},{\"poster_path\":\"\\/b9uYMMbm87IBFOq59pppvkkkgNg.jpg\",\"adult\":false,\"overview\":\"After the sudden death of his beloved wife, John Wick receives one last gift from her, a beagle puppy named Daisy, and a note imploring him not to forget how to love. But John's mourning is interrupted when his 1969 Boss Mustang catches the eye of sadistic thug Iosef Tarasov who breaks into his house and steals it, beating John unconscious in the process. Unwittingly, he has just reawakened one of the most brutal assassins the underworld has ever known.\",\"release_date\":\"2014-10-22\",\"genre_ids\":[28,53],\"id\":245891,\"original_title\":\"John Wick\",\"original_language\":\"en\",\"title\":\"John Wick\",\"backdrop_path\":\"\\/mFb0ygcue4ITixDkdr7wm1Tdarx.jpg\",\"popularity\":12.448565,\"vote_count\":2482,\"video\":false,\"vote_average\":7.06}],\"total_results\":19628,\"total_pages\":982}";

    OnTaskCompleted mTaskCompletedListener;
    JsonParser mJsonParser;
    int mAskHowMany;

    FetchList(OnTaskCompleted listener,
              JsonParser iParser,
              int iAskHowMany){
        mTaskCompletedListener = listener;
        mJsonParser = iParser;
        mAskHowMany = iAskHowMany;
    }

    @Override
    protected Object[] doInBackground(String... Uri) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String jsonStr = null;


        try {
            String u2 = "http://api.themoviedb.org/3/movie/popular/?language=en-US&api_key=ef24890fe794fe46f73df80ccc5a71bc";
            URL url = new URL(u2);//(Uri[0]);
            Log.e(LOG_TAG, url.toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a string;
            InputStream inputStream = urlConnection.getInputStream();
            int a = inputStream.available();
            String s = inputStream.toString();
            StringBuffer buffer = new StringBuffer();

            if (inputStream == null)
                return null;
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return null;
            }
            jsonStr = buffer.toString();

            Log.v(LOG_TAG, jsonStr);

            Object[] ret = mJsonParser.extractData(jsonStr, mAskHowMany);
            return ret;
        } catch (MalformedURLException iE) {
            Log.e(LOG_TAG, "mal formed url " + Uri + " " + iE.getMessage());
        } catch (ProtocolException iE) {
            Log.e(LOG_TAG, "ProtocolException " + urlConnection + " " + iE.getMessage());
        } catch (IOException iE) {
            Log.e(LOG_TAG, "url.openConnection() throw IOException " + iE.getMessage());
        } catch (JSONException iE) {
            Log.e(LOG_TAG, "extractData from json throw an exception" + iE.getMessage());
        }
        finally {

        }


        return null;
    }

    @Override
    protected void onPostExecute(Object[] ret) {
        mTaskCompletedListener.onTaskCompleted(ret);
    }
}
