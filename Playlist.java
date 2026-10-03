package com.CSC161.ArrayAndLinkedList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

/**
 * Playlist program that allows the user to add, remove,
 * count, play, shuffle, reverse, save, and load songs.
 */
public class PlayList {

	public static void main(String[] args) {

		Scanner keyboard = new Scanner(System.in);

		MyDoubleLinkedList<Song> playlist =
				new MyDoubleLinkedList<Song>();

		boolean running = true;

		System.out.println("Welcome to the Playlist Manager!");
		System.out.println("Choose an option from the menu below.");

		while (running) {

			printMenu();

			String choice = keyboard.nextLine().trim();

			switch (choice) {

				case "1":
					addSong(playlist, keyboard);
					break;

				case "2":
					removeSong(playlist, keyboard);
					break;

				case "3":
					printCount(playlist);
					break;

				case "4":
					playPlaylist(playlist);
					break;

				case "5":
					shuffle(playlist);
					break;

				case "6":
					reversePlaylist(playlist);
					break;

				case "7":
					savePlaylist(playlist, keyboard);
					break;

				case "8":
					loadPlaylist(playlist, keyboard);
					break;

				case "9":
					running = false;
					System.out.println("Goodbye!");
					break;

				default:
					System.out.println(
							"Invalid choice. Please enter 1 through 9.");
			}
		}

		keyboard.close();
	}


	/**
	 * Displays the main menu.
	 */
	private static void printMenu() {

		System.out.println();
		System.out.println("------ PLAYLIST MENU ------");
		System.out.println("1. Add Song");
		System.out.println("2. Remove Song");
		System.out.println("3. Count Songs");
		System.out.println("4. Play Playlist");
		System.out.println("5. Shuffle Playlist");
		System.out.println("6. Reverse Playlist");
		System.out.println("7. Save Playlist");
		System.out.println("8. Load Playlist");
		System.out.println("9. Quit");
		System.out.print("Enter choice: ");
	}


	/**
	 * Adds a song to the playlist.
	 */
	private static void addSong(
			MyDoubleLinkedList<Song> playlist,
			Scanner keyboard) {

		System.out.print("Enter song title: ");
		String title = keyboard.nextLine().trim();

		System.out.print("Enter artist: ");
		String artist = keyboard.nextLine().trim();

		if (title.isEmpty() || artist.isEmpty()) {
			System.out.println(
					"Title and artist cannot be empty.");
			return;
		}

		playlist.add(new Song(title, artist));

		System.out.println("Song added.");
	}


	/**
	 * Removes a song selected by the user.
	 */
	private static void removeSong(
			MyDoubleLinkedList<Song> playlist,
			Scanner keyboard) {

		if (playlist.isEmpty()) {
			System.out.println("The playlist is empty.");
			return;
		}

		playPlaylist(playlist);

		System.out.print(
				"Enter the number of the song to remove: ");

		String input = keyboard.nextLine().trim();

		try {

			int songNumber = Integer.parseInt(input);

			if (songNumber < 1 ||
					songNumber > playlist.count()) {

				System.out.println("Invalid song number.");
				return;
			}

			Song removed =
					playlist.remove(songNumber - 1);

			System.out.println("Removed: " + removed);
		}
		catch (NumberFormatException e) {

			System.out.println(
					"Please enter a valid number.");
		}
	}


	/**
	 * Prints the number of songs in the playlist.
	 */
	private static void printCount(
			MyDoubleLinkedList<Song> playlist) {

		int count = playlist.count();

		if (count == 1) {
			System.out.println(
					"There is 1 song in the playlist.");
		}
		else {
			System.out.println(
					"There are " + count +
					" songs in the playlist.");
		}
	}


	/**
	 * Prints all songs from first to last.
	 */
	private static void playPlaylist(
			MyDoubleLinkedList<Song> playlist) {

		if (playlist.isEmpty()) {
			System.out.println("The playlist is empty.");
			return;
		}

		System.out.println();
		System.out.println("Playlist:");

		for (int i = 0; i < playlist.count(); i++) {
			System.out.println(
					(i + 1) + ". " + playlist.get(i));
		}
	}


	/**
	 * Randomly rearranges the songs in the playlist.
	 */
	private static void shuffle(
			MyDoubleLinkedList<Song> playlist) {

		int size = playlist.count();

		if (size < 2) {
			System.out.println(
					"At least two songs are needed to shuffle.");
			return;
		}

		Random random = new Random();

		/*
		 * Use a temporary array to hold the songs.
		 */
		Song[] songs = new Song[size];

		for (int i = 0; i < size; i++) {
			songs[i] = playlist.get(i);
		}

		/*
		 * Fisher-Yates shuffle.
		 */
		for (int i = songs.length - 1; i > 0; i--) {

			int randomIndex =
					random.nextInt(i + 1);

			Song temp = songs[i];

			songs[i] = songs[randomIndex];

			songs[randomIndex] = temp;
		}

		/*
		 * Put the shuffled order back into
		 * the linked list.
		 */
		for (int i = 0; i < size; i++) {
			playlist.set(i, songs[i]);
		}

		System.out.println("Playlist shuffled.");
	}


	/**
	 * Reverses the actual order of the playlist.
	 */
	private static void reversePlaylist(
			MyDoubleLinkedList<Song> playlist) {

		if (playlist.isEmpty()) {
			System.out.println("The playlist is empty.");
			return;
		}

		playlist.reverse();

		System.out.println("Playlist reversed.");
	}


	/**
	 * Saves the playlist to a text file.
	 */
	private static void savePlaylist(
			MyDoubleLinkedList<Song> playlist,
			Scanner keyboard) {

		System.out.print(
				"Enter the filename to save: ");

		String filename =
				keyboard.nextLine().trim();

		if (filename.isEmpty()) {
			System.out.println("Invalid filename.");
			return;
		}

		try {

			PrintWriter output =
					new PrintWriter(filename);

			for (int i = 0;
					i < playlist.count();
					i++) {

				Song song = playlist.get(i);

				output.println(song.getTitle());
				output.println(song.getArtist());
			}

			output.close();

			System.out.println(
					"Playlist saved to " + filename);
		}
		catch (FileNotFoundException e) {

			System.out.println(
					"Unable to save the playlist.");
		}
		catch (SecurityException e) {

			System.out.println(
					"Unable to access that file.");
		}
	}


	/**
	 * Loads a previously saved playlist.
	 */
	private static void loadPlaylist(
			MyDoubleLinkedList<Song> playlist,
			Scanner keyboard) {

		System.out.print(
				"Enter the filename to load: ");

		String filename =
				keyboard.nextLine().trim();

		if (filename.isEmpty()) {
			System.out.println("Invalid filename.");
			return;
		}

		try {

			Scanner fileInput =
					new Scanner(new File(filename));

			MyDoubleLinkedList<Song> loadedPlaylist =
					new MyDoubleLinkedList<Song>();

			while (fileInput.hasNextLine()) {

				String title =
						fileInput.nextLine();

				/*
				 * Every title must have an artist.
				 */
				if (!fileInput.hasNextLine()) {

					fileInput.close();

					System.out.println(
							"Invalid playlist file.");

					return;
				}

				String artist =
						fileInput.nextLine();

				loadedPlaylist.add(
						new Song(title, artist));
			}

			fileInput.close();

			/*
			 * Only clear the original playlist
			 * after the file was successfully read.
			 */
			playlist.clear();

			for (int i = 0;
					i < loadedPlaylist.count();
					i++) {

				playlist.add(
						loadedPlaylist.get(i));
			}

			System.out.println("Playlist loaded.");
		}
		catch (FileNotFoundException e) {

			System.out.println("File not found.");
		}
		catch (SecurityException e) {

			System.out.println(
					"Unable to access that file.");
		}
	}
}
