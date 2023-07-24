package com.example.demo;

import com.example.spotifyApp.SpotifyApp;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(classes = SpotifyApp.class) // Replace SpotifyApp with your main application class

class SpotifyAppTest {

	@Test
	void contextLoads() {
	}

}
