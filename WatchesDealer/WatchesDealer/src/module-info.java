module WatchesDealer {
	opens main;
	opens admin;
	opens brand;
	opens database;
	opens user;
	opens watch;
	requires java.sql;
	requires javafx.graphics;
	requires javafx.controls;
	requires jfxtras.labs;
	requires javafx.base;
}