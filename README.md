ARCITECTURE
1. Core folder is an independent folder. When creating a new project u can simply copy paste it to new project so it's completely detached from business logic

2. Api layer for each screen are completely separated. So even though Species and Planet API response structure is same as people API we had created files again.




About App
The app should contain 2 main areas:
• Character Search (Home Screen)
• Character Details (Details Screen)
The following attributes are displayed for the character details:
• name
• birth_year
• height (in cm and feet/inches)
• name (species)
• language (species)
• homeworld (species)
• population (planets)
• films (movies the character appeared in)
• opening_crawl (detailed description of each movie)




FUNCTIONALITY
1. For character detail screen I am using 3 APIs to get data - Planet API, Species API, Film API.

2. Home world url is coming inside people list API. The url points to planet API. But when gone through planet API there is no key as homeworld.
 So thinking homeworld means Planet home city I am showing planet name inside home world field. Next choice would be to show url as this is what coming inside home world key of people list response.

Thinking this would not be suitable format for end user I am showing planet name instead along with "(Planet Name)" text on right side of homeworld to indicate that home world means Planet name to end user

3. Most of the APIs doesn't contains all the details. You can search for "Chewbacca" to see detail screen with all data as this person has all the details inside star war API and development was done using this person.

4. Mutliple opening crawls are coming for most of the characters so I am showing them all. Same goes for other fields

5. People Details screen is divided into three parts - Data coming from - People List, species list, and from film API.




ASSIGNMENT DOC
The Challenge

1.
Implement a small but scalable (!) app which interacts with the open Star Wars API at https://
https://swapi.dev /.
DONE

2.
The app should contain 2 main areas:
• Character Search (Home Screen) DONE
• Character Details (Details Screen) DONE
The following attributes should be displayed for the character details:
• name DONE
• birth_year DONE
• height (in cm and feet/inches) DONE
• name (species) DONE
• language (species) DONE
• homeworld (species) DONE
• population (planets) DONE
• films (movies the character appeared in) DONE
• opening_crawl (detailed description of each movie). DONE

Expected user flow -> DONE
On app start, the user lands on the character search screen.
The user can search for characters from the Star Wars universe. The result of the search should
display a character list.
When tapping on a character, details are displayed as defined above.


Technical requirements -> DONE
Please use Kotlin as the project language. The design of the UI, usage of 3rd party libraries and
supported API level are simply your choice, with exceptions mentioned in the resources section.
That said, don't forgot to approach this like a project that has to scale, so your project should
include a modern architectural approach, unit tests, dependency injection and whatever else
you consider important

Documentation - DONE
As part of the deliverable, please add a read.me file documenting your decisions (e.g. design
patterns, 3rd party library choices). This will help us understand your thought process and can
fuel further discussions during the interview process. Please be aware that your documentation
and explanations will be a big part of our evaluation.


Where to focus
We know that developing a case study takes a lot of time, so, please focus on the next quality
areas:
• Design a clean architecture solution. DONE
• Use the benefits of Kotlin, to write easy-to-read code. DONE
• Don’t forget your tests! - DONE
• Document your way of thinking through a readme. Taken decisions should
be presented here. DONE



KNOWN ISSUES
1. One memory Leak randomly happens when we minimize application.
Leak pattern: instance field android.app.Activity$1#this$0
    Description: Android Q added a new android.app.IRequestFinishCallback$Stub class. android.app.Activity creates an
    implementation of that interface as an anonymous subclass. That anonymous subclass has a reference to the activity.
    Another process is keeping the android.app.IRequestFinishCallback$Stub reference alive long after Activity.
    onDestroyed() has been called, causing the activity to leak

This is known issue acknowledged by google and they will fix it in future releases. Please visit below link for more info
In the below link see the description it is Memory leak pointed above and if you scroll down google team was able to produce it and they acknowledge
that they will fix it in future release. I had also posted screenshot of acknowledgement inside screenshot folder (Inside attachment)
https://issuetracker.google.com/issues/139738913


2. I had use Robolectric library and this library has issue right now which
   could be fixed by using JaCoco as Code Coverage inside android studio.

   If you don't set it then one test will fail only for View Model if you run test coverage for all modules combinedly if you just run test for all modules without coverage then it will not fail. This issue is logged inside Robolectric library and you can check it here

   https://github.com/robolectric/robolectric/issues/3023

   So if you want to test code coverage please check it separately.

3.  Application only supports portrait mode - Since the app was designed first with portrait orientation in mind so we restricted it to portrait mode and it will require few more days to address and fix issues happening due to orientation  change.

    If this needs to be done I will completely decouple View Model from fragment because rotating device will recreate fragment but View Model remains as it is.

    Keeping in mind "Where to focus" points provided in assignment I had put more focus on complete app and architecture hence added it in backlog.




Following technologies are used.
1. Kotlin with MVVM clean architecture, Lifecycle Extensions
2. Use Cases, Navigator, Couroutine, Extensions, Dagger,Synthetic properties, Data Binding.
3. I had used kotlin Scripts instead of Groovy
4. Robolectrick, Android X, Espresso, Mockito
5. App Compat, Constraint Layout, Coordinator Layout
6. Leak Canary, Kluent, Build Variants created with production, internal,dev environment.

7. The Application uses navigator architecture in which we had created one launcher activity and all the new screens as per client will be navigated from this activity using navigator.

8.
I had split gradle files, string files into smaller files to make them
simpler and all are connected inside build.gradle.kts. To see libraries
 used go to Dependencies.kt

9. Common themes are created inside style and are used across the screens
10. For Espresso please go through peopleListFragmentTest file.




