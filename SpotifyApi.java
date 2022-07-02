package com.bridgelabz.in;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class SpotifyApi {
	
	public String token;
	String user_id = "31g73feyh7giptqzmwszxkltxw74";
	String playlist_id = "4nKYMJwZRmP9ZThGj3zOiw";
	String track;
	@BeforeTest
	public void getToken() {
		token = "Bearer BQA6m7XbFDL-y0-9O2SJ8LwoX92qIXuqazNxY9q4vhHSM-hT6wcmmoW9dnvABPoD0JO7BADg5LeUDL1khMYDQ2XODK9HbM2kUkmZ0snMLzmN_bgEijv62PHXlQWf6KOCtFR03CWlyw9NWMDW7ZrRR-gDmNRwF54-GjcZrLwoI91a2M85s6ZaeYEVK_8_TADROKBYv1lejGwk0JtCz0jTmc0LxLDzmHfTB8EakB5xVLjTIpDmuO4wMf5clg2bHssnishfdMjtP8DVJxEy78Goy582hHJzY1u11nU9qE3VF8AZ9g";
		}
	
	@BeforeTest
	public void getTrack() {
		track = "spotify:track:77gskLti3ilCnuwlDq0m8j";
	}
	
	@Test(priority = 1)
	public void testGet_CurrentUsersProfile() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
							.accept(ContentType.JSON)
							.header("Authorization", token)
							.when()
							.get("https://api.spotify.com/v1/me");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	
	@Test(priority = 2)
	public void testGet_Users_Profile() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
							.accept(ContentType.JSON)
							.header("Authorization", token)
							.when()
							.get("	https://api.spotify.com/v1/users/" + user_id +"/");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	
	@Test(priority = 3)
	public void testCreate_Playlist() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
							.accept(ContentType.JSON)
							.header("Authorization", token)
							.body("{\r\n"
									+ "  \"name\": \"Aish Playlist\",\r\n"
									+ "  \"description\": \"New playlist description\",\r\n"
									+ "  \"public\": false\r\n"
									+ "}")
							.when()
							.post("https://api.spotify.com/v1/users/"+user_id+"/playlists");
		response.prettyPrint();
		response.then().assertThat().statusCode(201);
		//playListId = response.path("id");
	}
	
	@Test
	public void testGet_Playlist() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
							.accept(ContentType.JSON)
							.header("Authorization", token)
							.when()
							.get("	https://api.spotify.com/v1/playlists/"+playlist_id+"");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	
	@Test
	public void testGet_Users_Playlists() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
							.accept(ContentType.JSON)
							.header("Authorization", token)
							.when()
							.get("https://api.spotify.com/v1/users/"+user_id+"/playlists");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	
	@Test
	public void testGetCurrent_Users_Playlists() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
							.accept(ContentType.JSON)
							.header("Authorization", token)
							.when()
							.get("https://api.spotify.com/v1/me/playlists");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	
	@Test
	public void testChange_Playlist_Details() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
							.accept(ContentType.JSON)
							.header("Authorization", token)
							.body("{\r\n"
									+ "  \"name\": \"Arnavi Playlist \",\r\n"
									+ "  \"description\": \"Updated playlist description\",\r\n"
									+ "  \"public\": false\r\n"
									+ "}")
							.when()
							.put("https://api.spotify.com/v1/playlists/"+playlist_id+"");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	
	@Test
	public void testAdd_Items_to_Playlist() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
							.accept(ContentType.JSON)
							.header("Authorization", token)
							.queryParams("uris", track)
							.when()
							.post("https://api.spotify.com/v1/playlists/"+playlist_id+"/tracks");
		response.prettyPrint();
		response.then().assertThat().statusCode(201);
	}
	
	@Test
	public void testGet_Playlist_Items() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
							.accept(ContentType.JSON)
							.header("Authorization", token)
							.when()
							.get("https://api.spotify.com/v1/playlists/"+playlist_id+"/tracks");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	

	
	@Test
	public void  searchForItem() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", token)
				.queryParam("q","sid sriram")
				.queryParam("type","track")
				.when()
				.get("https://api.spotify.com/v1/playlists/"+playlist_id+"/tracks");
response.prettyPrint();
response.then().assertThat().statusCode(200);
		
	}
	@Test
	public void UpdatePlaylistItem() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", token)
				.body("{\n"+
						
						"  \"range_start\": 1,\n" +
						"	 \"insert_before\": 0,\n" +
						"	  \"range_length\": 2\n" +
							
						"}")
				.when()
				.put("https://api.spotify.com/v1/playlists/"+playlist_id+"/tracks");
response.prettyPrint();
response.then().assertThat().statusCode(200);

	
	} 
	@Test
	public void RemovePlaylistItem() {
		Response response = RestAssured.given().contentType(ContentType.JSON).accept(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", token)
				.body("{\"tracks\":[{\"uri\":\"spotify:track:2PvaburAUlhNHxVhw5lvOq\"}]}")
				.when()
				.delete("https://api.spotify.com/v1/playlists/"+playlist_id+"/tracks");
response.prettyPrint();
response.then().assertThat().statusCode(200);
	}
	@Test
	public void TrackId() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", token)
				.when()
				.get("https://api.spotify.com/v1/tracks/2PvaburAUlhNHxVhw5lvOq");
response.prettyPrint();
response.then().assertThat().statusCode(200);
		
	}
}
	