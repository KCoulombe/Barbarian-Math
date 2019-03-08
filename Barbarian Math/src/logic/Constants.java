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
	
	public static final String DATA_FOLDER_NAME = "./BarbarianMathData";
	public static final String EXTENTION_RULESET = ".ruleset";
	public static final String LABEL_MODIFIER_LOAD = "Load Modifier";
	
	
	public static final String TAG_MODIFIER = "TAG_MODIFIER";
	
	public static final String LABEL_MODIFIER_LOAD_BUTTON = "Load";
	public static final String ENTER_MODIFIER_LOAD = "ENTER_MODIFIER_LOAD";
	public static final String LABEL_NAME_FIELD = "Name:";
	public static final String TAG_NAME = "TAG_NAME";
	public static final String TAG_ATTRIBUTE = "TAG_ATTRIBUTE";
	public static final String LABEL_ATTRIBUTE_SELECT = "Attribute Used";
	public static final String TAG_INTEGER = "INTEGER";
	public static final String LABEL_COST_FIELD = "Cost Amount";
	public static final String ENTER_COST = "ENTER_COST";
	public static final String LABEL_ADD_BUTTON = "Add";
	public static final String LABEL_TYPE_DROP = "Type: ";
	public static final String ENTER_TYPE = "ENTER_TYPE";
	
	// start Tags from classes and other modifiers
	public static final String TAG_WEAPON_FINESSE = "Weapon Finesse";
	public static final String TAG_CLASS = "class";
	public static final String TAG_REACTION = "reaction";
	public static final String TAG_MAGIC = "magic";
	public static final String TAG_SPELL = "spell";
	public static final String TAG_SCALING = "scaling";
	public static final String LIMIT_ONCE_PER_TURN = "oncePerTurn";
	public static final String LIMIT_ON_HIT = "onHit":
	public static final String TAG_WIS_MOD ="wisMod";
	public static final String TAG_DEX_MOD ="dexMod";
	public static final String TAG_LIGHTNING = "lightning";
	public static final String TAG_ACID ="acid";
	public static final String TAG_SPELL_SLOT="spellslot";
	public static final String TAG_FULL_CASTER = "fullCaster";
	//End Tags from classes and other modifiers
}
