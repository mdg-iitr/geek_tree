# geek_tree
## MDG WOC Project
#### GeekTree

Geek-Tree is a project under Winter of Code, a month long programming project aimed to bring more students into open source development. It is organised each year by MDG and SDS labs of IIT Roorkee.  
### Features
---
Users can sign up into this app and specify their fields of interests, which they can change at a later stage as well. Based on their selected fields, they'll be shown posts on their *home screen*. Users can star the posts they like, and view them later under *pinned posts* as well. The app also allows users to add posts, and select the interests under which they want the post to be visible.  
A separate fragment, showing all the interests is also available, through which a user can view posts according to interests. He can also view the profiles of users who follow an interest.

### Development Environment
---
The app is entirely written using Kotlin in Android Studio.

### Architecture
---
* Architecture Pattern - **MVVM**
    * Model-View-ViewModel architecture pattern has been followed to retrieve posts, interest list of user, the list of users following a particular interest, and also their information. RecyclerView and CardView are used to display different lists in the app.
* [Architecture Components](https://developer.android.com/topic/libraries/architecture/)
    * [Livedata](https://developer.android.com/topic/libraries/architecture/livedata)
        * Data is being observed using *livedata*.
    * [ViewModel](https://developer.android.com/reference/android/arch/lifecycle/ViewModel)
        *  ViewModels process the DataSnapshot fetched from the FirebaseDatabase by LiveData, and emmit it, which is observed by the views. 
    *  [Navigation Architecture](https://developer.android.com/topic/libraries/architecture/navigation/)
        *  The app follows Single Activity Pattern, and navigation between the different fragments is executed using *navigation graph*.

### Firebase
---
Following Firebase components have been used:
* Firebase Authentication
  * For sign in
* Firebase Database
  * To save users' information, their interests, and posts.
* Firebase Storage
  * To save posts' pictures and profile image of users.
  
### Interface CallBack 
---
The app makes use of callback to check, during sign up, if a username already exists. This was used to make sure that a user signs up only if the username is unique.

### DataBinding
---
Databinding has been used to fetch the information of current user to display in his profile.


### Libraries Used
For cropping image  


    api 'com.theartofdev.edmodo:android-image-cropper:2.8.+'


For animated like button  

    implementation'com.github.jd-alexander:LikeButton:0.2.3'


For picasso, to load image into posts and profile image  

    implementation 'com.squareup.picasso:picasso:2.5.2'



### Acknowledgement

* [Kaustubh Trivedi](github.com/codekaust)
