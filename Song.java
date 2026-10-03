package com.CSC161.ArrayAndLinkedList;

/**
 * Represents a song in the playlist.
 * Each song contains a title and an artist.
 */
public class Song {

	private String title;
	private String artist;

	/**
	 * Creates a new Song.
	 *
	 * @param title the title of the song
	 * @param artist the artist of the song
	 */
	public Song(String title, String artist) {
		this.title = title;
		this.artist = artist;
	}

	/**
	 * Returns the song title.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Returns the artist.
	 */
	public String getArtist() {
		return artist;
	}

	/**
	 * Returns the song as a String.
	 */
	@Override
	public String toString() {
		return title + " - " + artist;
	}
}
