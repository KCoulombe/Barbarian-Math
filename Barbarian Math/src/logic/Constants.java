package logic;

import javax.swing.Icon;
/**
 * Contains all static identifier or control data
 * @author Tim
 *
 */
public class Constants {
	//default settings
	public static final int WINDOW_Y = 600;
	public static final int WINDOW_X = 800;
	//mode tags, used to convey which screen is in use.
	public static final String MODE_START = "MODE_START";
	public static final String MODE_MAIN = "MODE_MAIN";
	public static final String MODE_MODIFIER = "MODE_MODIFIER";
	public static final String MODE_BUILD = "MODE_BUILD";
	public static final String MODE_SAVE = "MODE_SAVE";
	public static final String MODE_LOAD = "MODE_LOAD";
	public static final String MODE_CALCULATE = "MODE_CALCULATE";
	
	public static final String START_BUTTON = "BUTTON_START";
	//action events for buttons
	public static final String GOTO_CALCULATOR_BUTTON = "GOTO_CALCULATOR_BUTTON";
	public static final String GOTO_BUILD_BUTTON = "GOTO_BUILD_BUTTON";
	public static final String GOTO_MODIFIER_BUTTON = "GOTO_MODIFIER_BUTTON";
	public static final String GOTO_SAVE_BUTTON = "GOTO_SAVE_BUTTON";
	public static final String GOTO_LOAD_BUTTON = "GOTO_LOAD_BUTTON";
	public static final String GOTO_MAIN_BUTTON = "GOTO_MAIN_BUTTON";
	//labels for buttons
	public static final String GOTO_MAIN_BUTTON_LABEL = "Start";
	public static final String GOTO_CALCULATOR_BUTTON_LABEL = "Calculate";
	public static final String GOTO_BUILD_BUTTON_LABEL = "Build Manager";
	public static final String GOTO_MODIFIER_BUTTON_LABEL = "Modifier Manager";
	public static final String GOTO_SAVE_BUTTON_LABEL = "Save";
	public static final String GOTO_LOAD_BUTTON_LABEL = "Load";
	public static final String DATA_FOLDER_NAME = "BarbarianMathData";
	public static final CharSequence EXTENTION_RULESET = null;

}
