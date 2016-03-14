# GitHubJobs
A simple Android application to search and view GitHub Jobs.  See also:  https://jobs.github.com/api .

Code structure/organization:

 * Packages “activity” and “fragment”:  Currently one activity with 4 fragments with an ActionBar in the activity.  ActionBar menu items are contextual to the fragments (including whether or not they have search filter applied).  A search bar was added for filtering saved searches and search results.  Fragments are re-used instead of re-created with each transaction for improved performance and reduced resource usage.  A “new search” form is available to create new searches.  All searches are saved.  Structure is in place to facilitate adding more fields to the search form.
 * Package “ui”:  Lists are presented using the RecyclerView and ViewHolder patterns with a custom divider (owing that, unlike ListView, RecyclerView does not have an automatic divider mechanism).  Image downloads are done asynchronously with recycler and caching support via Square’s Picasso package with a custom transformation applied to turn the images into thumbnails of uniform width while maintaining aspect ratio and to reduce caching resource usage.  In the “job details” view, all content has “linkable” transformations applied, thus any URL or other linkable text will be turned into clickable links (not just the company URL -- the description often has links in it as well).
 * Package “rest”:  REST interaction done asynchronously via LoopJ’s AsyncHttp package with custom JSON response callbacks.  Structure is in place to add other types of callbacks.
 * Package “dataset”:  Search definitions are saved and their results are cached into SQLite databases using the Contract and Helper patterns.  Datasets are collections of jobs defined by GitHubJob and GitHubJobs classes.  
 * Package “helper”:  Location services handle both pre-M and M/post-M permission check differences since the APK targets API levels 19 and higher.

Changes/next steps (in no particular order):

 * Add the dual-column list view, but make it toggle-able via a menu item button (I have some questions about this view, primarily concerned with the real-estate for each entry in a two-column situation).  I replaced this part of the challenge with the search bar, but having both options are definitely not mutually exclusive.
 * Allow the user to optionally name the saved search.
 * Add more fields to the new search form and modify the recycler view to show those terms in the display title.  At some point this may get ridiculously long, wherein the optional user-specified search name might be better as a required field.
 * When location services have been denied on M/post-M devices, the button is disabled, but not visibly so.  Grey it out?  Make it disappear?  I would prefer to overly the current image with the classic circle-with-a-slash.  I don’t think Android has this in the existing default distributed drawables but I may have overlooked them.
 * Prevent saving the same search twice.  Overwrite or retain the previous entry?
 * Add delete buttons to each saved search list item so the user can delete them.
 * Push notifications for saved searches, when new jobs are available.
 * Detect when the network is known to be turned off or unavailable so we don’t have to time out before reaching for the cache, if this is possible.  I haven’t yet looked into the visibility level of these system states in application space.
 * Add a placeholder image for companies that have not uploaded a logo.  Probably need a custom graphic for this -- I could not find anything suitable in the Android default distribution of drawables.
 * It might be cool to do a CardLayout on the job details view, allowing the user to select multiple jobs to view while in the previous screen (e.g. using long press to invoke multi-select).  We could show logo and details on each card.  But it would be super-cool if we had access to the same service that Google uses to auto-magically find images of the company’s building for calendar etc… and then use logo as a fallback.
 * Perhaps add pagination support, since the GitHub Jobs API supports it (e.g. we could load up 50 search results at a time and have a “load more results” button at the end of the list -- though this does mean that the search filter will only catch what we’ve paged in thus far).  This may not be worth the work considering that we are using recycler views and there are not a large number of jobs posted to GitHub at this time.  Probably worthwhile if we can anticipate extremely large numbers of job postings similar to other larger job seeker sites.
 * The BaseFragment probably should be converted to an interface.
 * Add the Android Lint suggestions:  allowBackup, Google app indexing, and remove the redundant parent layouts that I managed to rack up during refactorings :)
 * Add unit testing
 * I’ve tested screen rotations but not the low-memory situations, those should be tested as well
 * Create a signed release APK
 * Add GPL 3 to each file
 * If GitHub has an API to save your searches to your GitHub account, it would be worthwhile to add a login activity and push saved searches to the account.
 * If GitHub has an API for job posters, it would be cool add support for that.  I believe this is the paying part where GitHub makes some money on this, if I read it right.  If so, and if there is an API for it, then that might be under a partner program and may require a formal relationship with GitHub.  This would make more sense if this app were not open source.  A lot of ifs here, but I am just brainstorming.
