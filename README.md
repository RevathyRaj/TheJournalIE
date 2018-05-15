# TheJournalIE

AppName: The Journal.ie

Displays home screen to select publication

Select the publication to find the articles of that publication
Click on “Similar” to view the related articles
Select any of the article to read the details

Technical Details

• Used Picasso image downloading library to fetch the images

• Used Retrofit client library to make api calls

• Created interfaces (mappers), which acts as intermediate classes between
activities and api responses

• Placed all the strings in strings.xml so that they cannot be retrieved from
reverse engineering

• Created dimens.xml to declare all the margins and support multiple screen
resolutions

• Used okhttp cache control to store the response in cache and display offline

• Displays details once fetched when internet is connected and the same will
be displayed offline if there is no internet in the further calls

• If application is not connected to internet at all (not at least once), internet
error is displayed

• Used static app icon

• Re-used adapters(same adapters and layout files for HomeRiver and TagRiver
screens) to maintain code reusability and optimization

• “similar” view will not be displayed in TagRiver screen
  
  
