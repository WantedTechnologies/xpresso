package edu.stanford.nlp.tagger.maxent;

//MaxentTagger -- StanfordMaxEnt, A Maximum Entropy Toolkit
//Copyright (c) 2002-2010 Leland Stanford Junior University


//This program is free software; you can redistribute it and/or
//modify it under the terms of the GNU General Public License
//as published by the Free Software Foundation; either version 2
//of the License, or (at your option) any later version.

//This program is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.

//You should have received a copy of the GNU General Public License
//along with this program; if not, write to the Free Software
//Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.

//For more information, bug reports, fixes, contact:
//Christopher Manning
//Dept of Computer Science, Gates 1A
//Stanford CA 94305-9010
//USA
//Support/Questions: java-nlp-user@lists.stanford.edu
//Licensing: java-nlp-support@lists.stanford.edu
//http://www-nlp.stanford.edu/software/tagger.shtml

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.Reader;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.zip.GZIPInputStream;

import com.wantedtech.common.xpresso.x;
import com.wantedtech.common.xpresso.helpers.Helpers;
import com.wantedtech.common.xpresso.sentence.Sentence;
import com.wantedtech.common.xpresso.sentence.pos.en.stanford.AmbiguityClasses;
import com.wantedtech.common.xpresso.sentence.pos.en.stanford.ArrayMath;
import com.wantedtech.common.xpresso.sentence.pos.en.stanford.Dictionary;
import com.wantedtech.common.xpresso.sentence.pos.en.stanford.FeatureKey;
import com.wantedtech.common.xpresso.sentence.pos.en.stanford.LambdaSolve;
import com.wantedtech.common.xpresso.sentence.pos.en.stanford.LambdaSolveTagger;
import com.wantedtech.common.xpresso.sentence.pos.en.stanford.MaxentPosTagger;
import com.wantedtech.common.xpresso.sentence.pos.en.stanford.TTags;
import com.wantedtech.common.xpresso.token.Token;
import com.wantedtech.common.xpresso.types.list;
import com.wantedtech.common.xpresso.types.tuples.tuple2;


/**
* The main class for users to run, train, and test the part of speech tagger.
*
* You can tag things through the Java API or from the command line.
* The two English taggers included in this distribution are:
* <ul>
* <li> A bi-directional dependency network tagger in models/bidirectional-distsim-wsj-0-18.tagger.
*      Its accuracy was 97.32% on Penn Treebank WSJ secs. 22-24.</li>
* <li> A model using only left sequence information and similar but less
*      unknown words and lexical features as the previous model in
*      models/left3words-wsj-0-18.tagger. This tagger runs a lot faster.
*      Its accuracy was 96.92% on Penn Treebank WSJ secs. 22-24.</li>
* </ul>
*
* <h3>Using the Java API</h3>
* <dl>
* <dt>
* A MaxentTagger can be made with a constructor taking as argument the location of parameter files for a trained tagger: </dt>
* <dd> <code>MaxentTagger tagger = new MaxentTagger("models/left3words-wsj-0-18.tagger");</code></dd>
* <p>
* <dt>A default path is provided for the location of the tagger on the Stanford NLP machines:</dt>
* <dd><code>MaxentTagger tagger = new MaxentTagger(DEFAULT_NLP_GROUP_MODEL_PATH); </code></dd>
* <p>
* <dt>If you set the NLP_TAGGER_HOME environment variable,
* DEFAULT_NLP_GROUP_MODEL_PATH will instead point to the directory
* given in NLP_TAGGER_HOME.</dt>
* <p>
* <dt>To tag a Sentence and get a TaggedSentence: </dt>
* <dd><code>Sentence taggedSentence = tagger.tagSentence(Sentence sentence)</code></dd>
* <dd><code>Sentence taggedSentence = tagger.apply(Sentence sentence)</code></dd>
* <p>
* <dt>To tag a list of sentences and get back a list of tagged sentences:
* <dd><code> List taggedList = tagger.process(List sentences)</code></dd>
* <p>
* <dt>To tag a String of text and to get back a String with tagged words:</dt>
* <dd> <code>String taggedString = tagger.tagString("Here's a tagged string.")</code></dd>
* <p>
* <dt>To tag a string of <i>correctly tokenized</i>, whitespace-separated words and get a string of tagged words back:</dt>
* <dd> <code>String taggedString = tagger.tagTokenizedString("Here 's a tagged string .")</code></dd>
* </dl>
* <p>
* The <code>tagString</code> method uses the default tokenizer (PTBTokenizer).
* If you wish to control tokenization, you may wish to call
* {@link #tokenizeText(Reader, TokenizerFactory)} and then to call
* <code>process()</code> on the result.
* </p>
*
* <h3>Using the command line</h3>
*
* Tagging, testing, and training can all also be done via the command line.
* <h3>Training from the command line</h3>
* To train a model from the command line, first generate a property file:
* <pre>java edu.stanford.nlp.tagger.maxent.MaxentTagger -genprops </pre>
*
* This gets you a default properties file with descriptions of each parameter you can set in
* your trained model.  You can modify the properties file , or use the default options.  To train, run:
* <pre>java -mx1g edu.stanford.nlp.tagger.maxent.MaxentTagger -props myPropertiesFile.props </pre>
*
*  with the appropriate properties file specified; any argument you give in the properties file can also
*  be specified on the command line.  You must have specified a model using -model, either in the properties file
*  or on the command line, as well as a file containing tagged words using -trainFile.
*
* Useful flags for controlling the amount of output are -verbose, which prints extra debugging information,
* and -verboseResults, which prints full information about intermediate results.  -verbose defaults to false
* and -verboseResults defaults to true.
*
* <h3>Tagging and Testing from the command line</h3>
*
* Usage:
* For tagging (plain text):
* <pre>java edu.stanford.nlp.tagger.maxent.MaxentTagger -model &lt;modelFile&gt; -textFile &lt;textfile&gt; </pre>
* For testing (evaluating against tagged text):
* <pre>java edu.stanford.nlp.tagger.maxent.MaxentTagger -model &lt;modelFile&gt; -testFile &lt;testfile&gt; </pre>
* You can use the same properties file as for training
* if you pass it in with the "-props" argument. The most important
* arguments for tagging (besides "model" and "file") are "tokenize"
* and "tokenizerFactory". See below for more details.
*
* Note that the tagger assumes input has not yet been tokenized and by default tokenizes it using a default
* English tokenizer.  If your input has already been tokenized, use the flag "-tokenized".
*
* <p> Parameters can be defined using a Properties file
* (specified on the command-line with <code>-prop</code> <i>propFile</i>),
* or directly on the command line (by preceding their name with a minus sign
* ("-") to turn them into a flag. The following properties are recognized:
* </p>
* <table border="1">
* <tr><td><b>Property Name</b></td><td><b>Type</b></td><td><b>Default Value</b></td><td><b>Relevant Phase(s)</b></td><td><b>Description</b></td></tr>
* <tr><td>model</td><td>String</td><td>N/A</td><td>All</td><td>Path and filename where you would like to save the model (training) or where the model should be loaded from (testing, tagging).</td></tr>
* <tr><td>trainFile</td><td>String</td><td>N/A</td><td>Train</td><td>Path to the file holding the training data; specifying this option puts the tagger in training mode.  Only one of 'trainFile','testFile','texFile', and 'convertToSingleFile' may be specified.</td></tr>
* <tr><td>testFile</td><td>String</td><td>N/A</td><td>Test</td><td>Path to the file holding the test data; specifying this option puts the tagger in testing mode.  Only one of 'trainFile','testFile','texFile', and 'convertToSingleFile' may be specified.</td></tr>
* <tr><td>textFile</td><td>String</td><td>N/A</td><td>Tag</td><td>Path to the file holding the text to tag; specifying this option puts the tagger in tagging mode.  Only one of 'trainFile','testFile','textFile', and 'convertToSingleFile' may be specified.</td></tr>
* <tr><td>convertToSingleFile</td><td>String</td><td>N/A</td><td>N/A</td><td>Provided only for backwards compatibility, this option allows you to convert a tagger trained using a previous version of the tagger to the new single-file format.  The value of this flag should be the path for the new model file, 'model' should be the path prefix to the old tagger (up to but not including the ".holder"), and you should supply the properties configuration for the old tagger with -props (before these two arguments).</td></tr>
* <tr><td>genprops</td><td>boolean</td><td>N/A</td><td>N/A</td><td>Use this option to output a default properties file, containing information about each of the possible configuration options.</td></tr>
* <tr><td>delimiter</td><td>char</td><td>/</td><td>All</td><td>Delimiter character that separates word and part of speech tags.  For training and testing, this is the delimiter used in the train/test files.  For tagging, this is the character that will be inserted between words and tags in the output.</td></tr>
* <tr><td>encoding</td><td>String</td><td>UTF-8</td><td>All</td><td>Encoding of the read files (training, testing) and the output text files.</td></tr>
* <tr><td>tokenize</td><td>boolean</td><td>true</td><td>Tag,Test</td><td>Whether or not the file has been tokenized.  If this is true, the tagger assumes that white space separates all and only those things that should be tagged as separate tokens, and that the input is strictly one sentence per line.</td></tr>
* <tr><td>tokenizerFactory</td><td>String</td><td>edu.stanford.nlp.process.PTBTokenizer</td><td>Tag,Test</td><td>Fully qualified class name of the tokenizer to use.  edu.stanford.nlp.process.PTBTokenizer does basic English tokenization.</td></tr>
* <tr><td>tokenizerOptions</td><td>String</td><td></td><td>Tag,Test</td><td>Known options for the particular tokenizer used. A comma-separated list. For PTBTokenizer, options of interest include <code>americanize=false</code> and <code>asciiQuotes</code> (for German). Note that any choice of tokenizer options that conflicts with the tokenization used in the tagger training data will likely degrade tagger performance.</td></tr>
* <tr><td>arch</td><td>String</td><td>generic</td><td>Train</td><td>Architecture of the model, as a comma-separated list of options, some with a parenthesized integer argument written k here: this determines what features are sed to build your model.  Options are 'left3words', 'left5words', 'bidirectional', 'bidirectional5words', generic', 'sighan2005' (Chinese), 'german', 'words(k),' 'naacl2003unknowns', 'naacl2003conjunctions', wordshapes(k), motleyUnknown, suffix(k), prefix(k), prefixsuffix(k), capitalizationsuffix(k), distsim(s), chinesedictionaryfeatures(s), lctagfeatures, unicodeshapes(k). The left3words architectures are faster, but slightly less accurate, than the bidirectional architectures.  'naacl2003unknowns' was our traditional set of unknown word features, but you can now specify features more flexibility via the various other supported keywords. The 'shapes' options map words to equivalence classes, which slightly increase accuracy.</td></tr>
* <tr><td>lang</td><td>String</td><td>english</td><td>Train</td><td>Language from which the part of speech tags are drawn. This option determines which tags are considered closed-class (only fixed set of words can be tagged with a closed-class tag, such as prepositions). Defined languages are 'english' (Penn tagset), 'polish' (very rudimentary), 'chinese', 'arabic', 'german', and 'medline'.  </td></tr>
* <tr><td>openClassTags</td><td>String</td><td>N/A</td><td>Train</td><td>Space separated list of tags that should be considered open-class.  All tags encountered that are not in this list are considered closed-class.  E.g. format: "NN VB"</td></tr>
* <tr><td>closedClassTags</td><td>String</td><td>N/A</td><td>Train</td><td>Space separated list of tags that should be considered closed-class.  All tags encountered that are not in this list are considered open-class.</td></tr>
* <tr><td>learnClosedClassTags</td><td>boolean</td><td>false</td><td>Train</td><td>If true, induce which tags are closed-class by counting as closed-class tags all those tags which have fewer unique word tokens than closedClassTagThreshold. </td></tr>
* <tr><td>closedClassTagThreshold</td><td>int</td><td>int</td><td>Train</td><td>Number of unique word tokens that a tag may have and still be considered closed-class; relevant only if learnClosedClassTags is true.</td></tr>
* <tr><td>sgml</td><td>boolean</td><td>false</td><td>Tag, Test</td><td>Very basic tagging of the contents of all sgml fields; for more complex mark-up, consider using the xmlInput option.</td></tr>
* <tr><td>xmlInput</td><td>String</td><td></td><td>Tag, Test</td><td>Give a space separated list of tags in an XML file whose content you would like tagged.  Any internal tags that appear in the content of fields you would like tagged will be discarded; the rest of the XML will be preserved and the original text of specified fields will be replaced with the tagged text.</td></tr>
* <tr><td>xmlOutput</td><td>String</td><td>""</td><td>Tag</td><td>If a path is given, the tagged data be written out to the given file in xml.  If non-empty, each word will be written out within a word tag, with the part of speech as an attribute.  If original input was XML, this will just appear in the field where the text originally came from.  Otherwise, word tags will be surrounded by sentence tags as well.  E.g., &lt;sentence id="0"&gt;&lt;word id="0" pos="NN"&gt;computer&lt;/word&gt;&lt;/sentence&gt;</td></tr>
* <tr><td>tagInside</td><td>String</td><td>""</td><td>Tag</td><td>Tags inside elements that match the regular expression given in the String.</td></tr>
* <tr><td>search</td><td>String</td><td>cg</td><td>Train</td><td>Specify the search method to be used in the optimization method for training.  Options are 'cg' (conjugate gradient) or 'iis' (improved iterative scaling).</td></tr>
* <tr><td>sigmaSquared</td><td>double</td><td>0.5</td><td>Train</td><td>Sigma-squared smoothing/regularization parameter to be used for conjugate gradient search.  Default usually works reasonably well.</td></tr>
* <tr><td>iterations</td><td>int</td><td>100</td><td>Train</td><td>Number of iterations to be used for improved iterative scaling.</td></tr>
* <tr><td>rareWordThresh</td><td>int</td><td>5</td><td>Train</td><td>Words that appear fewer than this number of times during training are considered rare words and use extra rare word features.</td></tr>
* <tr><td>minFeatureThreshold</td><td>int</td><td>5</td><td>Train</td><td>Features whose history appears fewer than this number of times are discarded.</td></tr>
* <tr><td>curWordMinFeatureThreshold</td><td>int</td><td>2</td><td>Train</td><td>Words that occur more than this number of times will generate features with all of the tags they've been seen with.</td></tr>
* <tr><td>rareWordMinFeatureThresh</td><td>int</td><td>10</td><td>Train</td><td>Features of rare words whose histories occur fewer than this number of times are discarded.</td></tr>
* <tr><td>veryCommonWordThresh</td><td>int</td><td>250</td><td>Train</td><td>Words that occur more than this number of times form an equivalence class by themselves.  Ignored unless you are using ambiguity classes.</td></tr>
* <tr><td>debug</td><td>boolean</td><td>boolean</td><td>All</td><td>Whether to write debugging information (words, top words, unknown words).  Useful for error analysis.</td></tr>
* <tr><td>debugPrefix</td><td>String</td><td>N/A</td><td>All</td><td>File (path) prefix for where to write out the debugging information (relevant only if debug=true).</td></tr>
* </table>
* <p/>
*
* @author Kristina Toutanova
* @author Miler Lee
* @author Joseph Smarr
* @author Anna Rafferty
* @author Michel Galley
* @author Christopher Manning
* @author John Bauer
*/
public class MaxentTagger {

/**
 * The directory from which to get taggers when using
 * DEFAULT_NLP_GROUP_MODEL_PATH.  Normally set to the location of
 * the latest left3words tagger on the NLP machines, but can be
 * changed by setting the environment variable TAGGER_HOME.
 */
public static final String TAGGER_HOME = ((System.getenv("NLP_TAGGER_HOME") != null) ?
                                          System.getenv("NLP_TAGGER_HOME") :
                                          "/u/nlp/data/pos-tagger/wsj3t0-18-left3words");
public static final String DEFAULT_NLP_GROUP_MODEL_PATH = new File(TAGGER_HOME, "left3words-wsj-0-18.tagger").getPath();
public static final String DEFAULT_DISTRIBUTION_PATH = "models/left3words-wsj-0-18.tagger";

/**
 * Initializer that loads the tagger.
 *
 * @param modelFile Where to initialize the tagger from.
 *        Most commonly, this is the filename of the trained model, for example, <code>
 *        /u/nlp/data/pos-tagger/wsj3t0-18-left3words/left3words-wsj-0-18.tagger
 *        </code>.  However, if it starts with "https?://" it will be
 *        interpreted as a URL, and if it starts with "jar:" it will be
 *        taken as a resources in the /models/ path of the current jar file.
 * @param config TaggerConfig based on command-line arguments
 * @param printLoading Whether to print a message saying what model file is being loaded and how long it took when finished.
 * @throws IOException if IO problem
 * @throws ClassNotFoundException when there are errors loading a tagger
 */
public MaxentTagger(String modelFile) {
  this(modelFile, TaggerConfig.argsToProperties("-model", modelFile));
}

public MaxentTagger(MaxentPosTagger.Model model) {
	this(model.fileName, TaggerConfig.argsToProperties("-model", model.fileName));
}


public MaxentTagger(String modelFile, Properties config) {
    readModelAndInit(config, modelFile);
}

final Dictionary dict = new Dictionary();
TTags tags;

/**
 * Will return the index of a tag, adding it if it doesn't already exist
 */
public int addTag(String tag) {
  return tags.add(tag);
}
/**
 * Will return the index of a tag if known, -1 if not already known
 */
public int getTagIndex(String tag) {
  return tags.getIndex(tag);
}

public int numTags() {
  return tags.getSize();
}

public String getTag(int index) {
  return tags.getTag(index);
}

public Set<String> tagSet() {
  return tags.tagSet();
}

byte[][] fnumArr;
AmbiguityClasses ambClasses;
final boolean alltags = false;
final HashMap<String, HashSet<String>> tagTokens = new HashMap<String, HashSet<String>>();

static final int RARE_WORD_THRESH = 5;
static final int MIN_FEATURE_THRESH = 5;
static final int CUR_WORD_MIN_FEATURE_THRESH = 2;
static final int RARE_WORD_MIN_FEATURE_THRESH = 10;
static final int VERY_COMMON_WORD_THRESH = 250;

static final boolean OCCURRING_TAGS_ONLY = false;
static final boolean POSSIBLE_TAGS_ONLY = false;

double defaultScore;

int leftContext;
int rightContext;
int sent_size;
Sentence sent;
protected String[] finalTags;
private volatile History history;
protected PairsHolder pairs = new PairsHolder();
private int endSizePairs;

Extractors extractors;
Extractors extractorsRare;

int numUnknown;

protected volatile Map<String,double[]> localScores = new HashMap<String,double[]>();
protected volatile double[][] localContextScores;

private LambdaSolveTagger prob;

List<Map<String, int[]>> fAssociations = Helpers.newArrayList();

TaggerConfig config;

String tagSeparator;
String encoding;

/**
 * Determines which words are considered rare.  All words with count
 * in the training data strictly less than this number (standardly, &lt; 5) are
 * considered rare.
 */
private int rareWordThresh = RARE_WORD_THRESH;

/**
 * Determines which features are included in the model.  The model
 * includes features that occurred strictly more times than this number
 * (standardly, &gt; 5) in the training data.  Here I look only at the
 * history (not the tag), so the history appearing this often is enough.
 */
int minFeatureThresh = MIN_FEATURE_THRESH;

/**
 * This is a special threshold for the current word feature.
 * Only words that have occurred strictly &gt; this number of times
 * in total will generate word features with all of their occurring tags.
 * The traditional default was 2.
 */
int curWordMinFeatureThresh = CUR_WORD_MIN_FEATURE_THRESH;

/**
 * Determines which rare word features are included in the model.
 * The features for rare words have a strictly higher support than
 * this number are included. Traditional default is 10.
 */
int rareWordMinFeatureThresh = RARE_WORD_MIN_FEATURE_THRESH;

/**
 * If using tag equivalence classes on following words, words that occur
 * strictly more than this number of times (in total with any tag)
 * are sufficiently frequent to form an equivalence class
 * by themselves. (Not used unless using equivalence classes.)
 *
 * There are places in the code (ExtractorAmbiguityClass.java, for one)
 * that assume this value is constant over the life of a tagger.
 */
int veryCommonWordThresh = VERY_COMMON_WORD_THRESH;


int xSize;
int ySize;
public boolean occurringTagsOnly = OCCURRING_TAGS_ONLY;
public boolean possibleTagsOnly = POSSIBLE_TAGS_ONLY;

private boolean initted = false;

static final boolean VERBOSE = false;

//ANDRIY

protected static final String naTag = "NA";
private static final String[] naTagArr = { naTag };

static String toNice(String s) {
    if (s == null) {
      return naTag;
    } else {
      return s;
    }
  }

protected String[] stringTagsAt(int pos) {
  if ((pos < this.leftContext) || (pos >= this.sent_size + this.leftContext)) {
    return naTagArr;
  }

  String[] arr1;
  
  String word = this.sent.get(pos - leftContext).toString();
  if (dict.isUnknown(word)) {
    Set<String> open = this.tags.getOpenTags();  // todo: really want array of String or int here
    arr1 = open.toArray(new String[open.size()]);
  } else {
    arr1 = this.dict.getTags(word);
  }
  arr1 = this.tags.deterministicallyExpandTags(arr1);
  return arr1;
}

public int[] getPossibleValues(int pos) {
  String[] arr1 = stringTagsAt(pos);
  int[] arr = new int[arr1.length];
  for (int i = 0; i < arr.length; i++) {
    arr[i] = this.tags.getIndex(arr1[i]);
  }

  return arr;
}

private void runTagInference() {
  initializeScorer();
  int[] bestTags = bestSequence();
  finalTags = new String[bestTags.length];
  for (int j = 0; j < this.sent_size; j++) {
    finalTags[j] = this.tags.getTag(bestTags[j + leftContext]);
  }
}

private Sentence getTaggedSentence() {
  for (int j = 0; j < sent_size - 1; j++) {
    String tag = finalTags[j];
    Token w = sent.get(j);
    w.annotate("pos", tag);
    sent.setAt(j).value(w);
  }
  sent.del(sent_size-1);
  return sent;
}

// do initializations for the TagScorer interface
protected void initializeScorer() {
  endSizePairs = 0;
  pairs.setSize(sent_size);
  for (int i = 0; i < sent_size; i++)
    pairs.setWord(i,sent.get(i).toString());
  endSizePairs += sent_size;
}

private void setHistory(int current, History h, int[] tags) {
    //writes over the tags in the last thing in pairs

    int left = leftContext;
    int right = rightContext;

    for (int j = current - left; j <= current + right; j++) {
      if (j < left) {
        continue;
      } //but shouldn't happen
      if (j >= sent_size + left) {
        break;
      } //but shouldn't happen
      h.setTag(j - left, this.tags.getTag(tags[j]));
    }
  }

LambdaSolve getLambdaSolve() {
    return prob;
  }

private double[] getExactHistories(History h, List<tuple2<Integer,Extractor>> extractors, List<tuple2<Integer,Extractor>> extractorsRare) {
  double[] scores = new double[this.ySize];
  int szCommon = this.extractors.size();

  for (tuple2<Integer,Extractor> e : extractors) {
    int kf = e.key;
    Extractor ex = e.value;
    String val = ex.extract(h);
    int[] fAssociations = this.fAssociations.get(kf).get(val);
    if (fAssociations != null) {
      for (int i = 0; i < this.ySize; i++) {
        int fNum = fAssociations[i];
        if (fNum > -1) {
          scores[i] += this.getLambdaSolve().lambda[fNum];
        }
      }
    }
  }
  if (extractorsRare != null) {
    for (tuple2<Integer,Extractor> e : extractorsRare) {
      int kf = e.key;
      Extractor ex = e.value;
      String val = ex.extract(h);
      int[] fAssociations = this.fAssociations.get(kf+szCommon).get(val);
      if (fAssociations != null) {
        for (int i = 0; i < this.ySize; i++) {
          int fNum = fAssociations[i];
          if (fNum > -1) {
            scores[i] += this.getLambdaSolve().lambda[fNum];
          }
        }
      }
    }
  }
  return scores;
}

private double[] getHistories(String[] tags, History h, List<tuple2<Integer,Extractor>> extractors, List<tuple2<Integer,Extractor>> extractorsRare) {
    return getExactHistories(h, extractors, extractorsRare);
}

/* Package access so it doesn't appear in public API. */
boolean isRare(String word) {
  return dict.sum(word) < rareWordThresh;
}

// This precomputes scores of local features (localScores).
protected double[] getHistories(String[] tags, History h) {
  boolean rare = this.isRare(ExtractorFrames.cWord.extract(h));
  Extractors ex = this.extractors, exR = this.extractorsRare;
  String w = pairs.getWord(h.current);
  double[] lS, lcS;
  lS = localScores.get(w);
  if (lS == null) {
    lS = getHistories(tags, h, ex.local, rare ? exR.local : null);
    localScores.put(w,lS);
  } else if (lS.length != tags.length) {
    // This case can occur when a word was given a specific forced
    // tag, and then later it shows up without the forced tag.
    // its features rather than use the cache, just in case the tag
    // given is not the same tag as before
    lS = getHistories(tags, h, ex.local, rare ? exR.local : null);
    if (tags.length > 1) {
      localScores.put(w,lS);
    }
  }
  if((lcS = localContextScores[h.current]) == null) {
    lcS = getHistories(tags, h, ex.localContext, rare ? exR.localContext : null);
    localContextScores[h.current] = lcS;
    ArrayMath.pairwiseAddInPlace(lcS,lS);
  }
  double[] totalS = getHistories(tags, h, ex.dynamic, rare ? exR.dynamic : null);
  ArrayMath.pairwiseAddInPlace(totalS,lcS);
  return totalS;
}

private double[] getExactScores(History h) {
    String[] tags = stringTagsAt(h.current - h.start + leftContext);
    double[] histories = getHistories(tags, h); // log score for each tag
    ArrayMath.logNormalize(histories);
    double[] scores = new double[tags.length];
    for (int j = 0; j < tags.length; j++) {
      // score the j-th tag
      String tag = tags[j];
      int tagindex = this.tags.getIndex(tag);
      scores[j] = histories[tagindex];
    }
    return scores;
  }

// This scores the current assignment in PairsHolder at
// current position h.current (returns normalized scores)
private double[] getScores(History h) {
  return getExactScores(h);
}

public double[] scoresOf(int[] tags, int pos) {
  history.init(endSizePairs - sent_size, endSizePairs - 1, endSizePairs - sent_size + pos - leftContext);
  setHistory(pos, history, tags);
  return getScores(history);
}

private int[] bestSequence() {
  // Set up tag options
  int length = this.sent_size;
  int leftWindow = this.leftContext;
  int rightWindow = this.rightContext;
  int padLength = length + leftWindow + rightWindow;
  int[][] tags = new int[padLength][];
  int[] tagNum = new int[padLength];
  for (int pos = 0; pos < padLength; pos++) {
    tags[pos] = getPossibleValues(pos);
    tagNum[pos] = tags[pos].length;
  }

  int[] tempTags = new int[padLength];

  // Set up product space sizes
  int[] productSizes = new int[padLength];

  int curProduct = 1;
  for (int i = 0; i < leftWindow + rightWindow; i++) {
    curProduct *= tagNum[i];
  }
  for (int pos = leftWindow + rightWindow; pos < padLength; pos++) {
    if (pos > leftWindow + rightWindow) {
      curProduct /= tagNum[pos - leftWindow - rightWindow - 1]; // shift off
    }
    curProduct *= tagNum[pos]; // shift on
    productSizes[pos - rightWindow] = curProduct;
  }

  // Score all of each window's options
  double[][] windowScore = new double[padLength][];
  for (int pos = leftWindow; pos < leftWindow + length; pos++) {
    windowScore[pos] = new double[productSizes[pos]];
    Arrays.fill(tempTags, tags[0][0]);

    for (int product = 0; product < productSizes[pos]; product++) {
      int p = product;
      int shift = 1;
      for (int curPos = pos + rightWindow; curPos >= pos - leftWindow; curPos--) {
        tempTags[curPos] = tags[curPos][p % tagNum[curPos]];
        p /= tagNum[curPos];
        if (curPos > pos) {
          shift *= tagNum[curPos];
        }
      }

      // Here now you get ts.scoresOf() for all classifications at a position at once, wwhereas the old code called ts.scoreOf() on each item.
      // CDM May 2007: The way this is done gives incorrect results if there are repeated values in the values of ts.getPossibleValues(pos) -- in particular if the first value of the array is repeated later.  I tried replacing it with the modulo version, but that only worked for left-to-right, not bidirectional inference, but I still think that if you sorted things out, you should be able to do it with modulos and the result would be conceptually simpler and robust to repeated values.  But in the meantime, I fixed the POS tagger to not give repeated values (which was a bug in the tagger).
      // if (product % tagNum[pos] == 0) {
      if (tempTags[pos] == tags[pos][0]) {
        // get all tags at once
        double[] scores = this.scoresOf(tempTags, pos);
        // fill in the relevant windowScores
        for (int t = 0; t < tagNum[pos]; t++) {
          windowScore[pos][product + t * shift] = scores[t];
        }
      }
    }
  }

  // Set up score and backtrace arrays
  double[][] score = new double[padLength][];
  int[][] trace = new int[padLength][];
  for (int pos = 0; pos < padLength; pos++) {
    score[pos] = new double[productSizes[pos]];
    trace[pos] = new int[productSizes[pos]];
  }

  // Do forward Viterbi algorithm

  // loop over the classification spot
  //System.err.println();
  for (int pos = leftWindow; pos < length + leftWindow; pos++) {
    //System.err.print(".");
    // loop over window product types
    for (int product = 0; product < productSizes[pos]; product++) {
      // check for initial spot
      if (pos == leftWindow) {
        // no predecessor type
        score[pos][product] = windowScore[pos][product];
        trace[pos][product] = -1;
      } else {
        // loop over possible predecessor types
        score[pos][product] = Double.NEGATIVE_INFINITY;
        trace[pos][product] = -1;
        int sharedProduct = product / tagNum[pos + rightWindow];
        int factor = productSizes[pos] / tagNum[pos + rightWindow];
        for (int newTagNum = 0; newTagNum < tagNum[pos - leftWindow - 1]; newTagNum++) {
          int predProduct = newTagNum * factor + sharedProduct;
          double predScore = score[pos - 1][predProduct] + windowScore[pos][product];
          if (predScore > score[pos][product]) {
            score[pos][product] = predScore;
            trace[pos][product] = predProduct;
          }
        }
      }
    }
  }

  // Project the actual tag sequence
  double bestFinalScore = Double.NEGATIVE_INFINITY;
  int bestCurrentProduct = -1;
  for (int product = 0; product < productSizes[leftWindow + length - 1]; product++) {
    if (score[leftWindow + length - 1][product] > bestFinalScore) {
      bestCurrentProduct = product;
      bestFinalScore = score[leftWindow + length - 1][product];
    }
  }
  int lastProduct = bestCurrentProduct;
  for (int last = padLength - 1; last >= length - 1 && last >= 0; last--) {
    tempTags[last] = tags[last][lastProduct % tagNum[last]];
    lastProduct /= tagNum[last];
  }
  for (int pos = leftWindow + length - 2; pos >= leftWindow; pos--) {
    int bestNextProduct = bestCurrentProduct;
    bestCurrentProduct = trace[pos + 1][bestNextProduct];
    tempTags[pos - leftWindow] = tags[pos - leftWindow][bestCurrentProduct / (productSizes[pos] / tagNum[pos - leftWindow])];
  }
  return tempTags;
}


//ANDRIY

public static final String EOS_TAG = ".$$.";
public static final String EOS_WORD = ".$.";

/**
 * Tags the input {@link Sentence} and returns the tagged version.
 *
 * @param sent The untagged input {@link Sentence}
 * @return the tagged sentence
 */
public Sentence tagSentence(Sentence sent) {
  pairs = new PairsHolder();
  history = new History(pairs, this.extractors);
  this.sent = sent;
  sent.append(new Token(EOS_WORD));
  this.sent_size = x.len(sent);
  
  //the eos are assumed already there
  localContextScores = new double[sent_size][];
  for (int i = 0; i < sent_size - 1; i++) {
    if (this.dict.isUnknown(sent.get(i).toString())) {
      numUnknown++;
    }
  }
  
  runTagInference();

  sent = getTaggedSentence();
  
  return sent;
}

void init(TaggerConfig config) {
    if (initted) return;  // TODO: why not reinit?

    this.config = config;

    String lang, arch;
    String[] openClassTags, closedClassTags;

    if (config == null) {
      lang = "english";
      arch = "left3words";
      openClassTags = new String[0];
      closedClassTags = new String[0];
    } else {

      lang = config.getLang();
      arch = config.getArch();
      openClassTags = config.getOpenClassTags();
      closedClassTags = config.getClosedClassTags();

      if (((openClassTags.length > 0) && !lang.equals("")) || ((closedClassTags.length > 0) && !lang.equals("")) || ((closedClassTags.length > 0) && (openClassTags.length > 0))) {
        throw new RuntimeException("At least two of lang (\"" + lang + "\"), openClassTags (length " + openClassTags.length + ": " + Arrays.toString(openClassTags) + ")," +
            "and closedClassTags (length " + closedClassTags.length + ": " + Arrays.toString(closedClassTags) + ") specified---you must choose one!");
      } else if ((openClassTags.length == 0) && lang.equals("") && (closedClassTags.length == 0) && ! config.getLearnClosedClassTags()) {
        System.err.println("warning: no language set, no open-class tags specified, and no closed-class tags specified; assuming ALL tags are open class tags");
      }
    }

    if (openClassTags.length > 0) {
      tags = new TTags();
      tags.setOpenClassTags(openClassTags);
    } else if (closedClassTags.length > 0) {
      tags = new TTags();
      tags.setClosedClassTags(closedClassTags);
    } else {
      tags = new TTags(lang);
    }

    defaultScore = lang.equals("english") ? 1.0 : 0.0;

    if (config != null) {
      rareWordThresh = config.getRareWordThresh();
      minFeatureThresh = config.getMinFeatureThresh();
      curWordMinFeatureThresh = config.getCurWordMinFeatureThresh();
      rareWordMinFeatureThresh = config.getRareWordMinFeatureThresh();
      veryCommonWordThresh = config.getVeryCommonWordThresh();
      occurringTagsOnly = config.occurringTagsOnly();
      possibleTagsOnly = config.possibleTagsOnly();
      // System.err.println("occurringTagsOnly: "+occurringTagsOnly);
      // System.err.println("possibleTagsOnly: "+possibleTagsOnly);

      if(config.getDefaultScore() >= 0)
        defaultScore = config.getDefaultScore();
    }

    if (config == null || config.getMode() == TaggerConfig.Mode.TRAIN) {
      // initialize the extractors based on the arch variable
      // you only need to do this when training; otherwise they will be
      // restored from the serialized file
      extractors = new Extractors(ExtractorFrames.getExtractorFrames(arch));
      extractorsRare = new Extractors(ExtractorFramesRare.getExtractorFramesRare(arch, tags));

      setExtractorsGlobal();
    }

    ambClasses = new AmbiguityClasses(tags);

    tagSeparator = this.config.getTagSeparator();
    encoding = this.config.getEncoding();
    history = new History(pairs, this.extractors);
    
    initted = true;
  }

// Read the extractors from a stream.
private void readExtractors(InputStream file) throws IOException, ClassNotFoundException {
  ObjectInputStream in = new ObjectInputStream(file);
  extractors = (Extractors) in.readObject();
  extractorsRare = (Extractors) in.readObject();
  extractors.initTypes();
  extractorsRare.initTypes();
  int left = extractors.leftContext();
  int left_u = extractorsRare.leftContext();
  if (left_u > left) {
    left = left_u;
  }
  leftContext = left;
  int right = extractors.rightContext();
  int right_u = extractorsRare.rightContext();
  if (right_u > right) {
    right = right_u;
  }
  rightContext = right;

  setExtractorsGlobal();
}

// Sometimes there is data associated with the tagger (such as a
// dictionary) that we don't want saved with each extractor.  This
// call lets those extractors get that information from the tagger
// after being loaded from a data file.
private void setExtractorsGlobal() {
  extractors.setGlobalHolder(this);
  extractorsRare.setGlobalHolder(this);
}

/** The directory in a jar file in which to find a tagger resource specified by jar:file */
public static final String JAR_TAGGER_PATH = "/models/";

/** Used for getting tagger models from any of file, URL or jar resource. */
DataInputStream getTaggerDataInputStream(String modelFileOrUrl) throws IOException {
  InputStream is;
  if (modelFileOrUrl.matches("https?://.*")) {
    URL u = new URL(modelFileOrUrl);
    is = new DataInputStream(u.openStream());
  } else  if (modelFileOrUrl.matches("jar:.*")) {
    is = getClass().getResourceAsStream(JAR_TAGGER_PATH + modelFileOrUrl.substring(4)); // length of "jar:"
  } else {
    is = new FileInputStream(modelFileOrUrl);
  }
  if (modelFileOrUrl.endsWith(".gz")) {
    is = new GZIPInputStream(is);
  }
  is = new BufferedInputStream(is);
  return new DataInputStream(is);
}

/** This reads the complete tagger from a single model stored in a file, at a URL,
 *  or as a resource
 *  in a jar file, and inits the tagger using a
 *  combination of the properties passed in and parameters from the file.
 *  <p>
 *  <i>Note for the future:</i> This assumes that the TaggerConfig in the file
 *  has already been read and used.  This work is done inside the
 *  constructor of TaggerConfig.  It might be better to refactor
 *  things so that is all done inside this method, but for the moment
 *  it seemed better to leave working code alone [cdm 2008].
 *
 *  @param config The tagger config
 *  @param modelFileOrUrl The name of the model file. This routine opens and closes it.
 *  @param printLoading Whether to print a message saying what model file is being loaded and how long it took when finished.
 *  @throws IOException If I/O errors, etc.
 *  @throws ClassNotFoundException especially for incompatible tagger formats
 */
protected void readModelAndInit(String modelFileOrUrl) throws IOException, ClassNotFoundException {
  // first check can open file ... or else leave with exception
  DataInputStream rf = getTaggerDataInputStream(modelFileOrUrl);

  // if (VERBOSE) {
  //   System.err.println(" length of model holder " + new File(modelFileOrUrl).length());
  // }

  readModelAndInit(config, rf);
  rf.close();
}

/** This reads the complete tagger from a single model stored in a file, at a URL,
 *  or as a resource in a jar file, and inits the tagger using a
 *  combination of the properties passed in and parameters from the file.
 *  <p>
 *  <i>Note for the future:</i> This assumes that the TaggerConfig in the file
 *  has already been read and used.  This work is done inside the
 *  constructor of TaggerConfig.  It might be better to refactor
 *  things so that is all done inside this method, but for the moment
 *  it seemed better to leave working code alone [cdm 2008].
 *
 *  @param config The tagger config
 *  @param modelFileOrUrl The name of the model file. This routine opens and closes it.
 *  @param printLoading Whether to print a message saying what model file is being loaded and how long it took when finished.
 *  @throws RuntimeIOException if I/O errors or serialization errors
 */
protected void readModelAndInit(Properties config, String modelFileOrUrl) {
  try {
    // first check can open file ... or else leave with exception
    DataInputStream rf = new DataInputStream(TaggerConfig.getInputStreamFromURLOrClasspathOrFileSystem(modelFileOrUrl));

    readModelAndInit(config, rf);
    rf.close();
  } catch (IOException e) {
    throw new RuntimeException("Unrecoverable error while loading a tagger model", e);
  }
}

/** This reads the complete tagger from a single model file, and inits
 *  the tagger using a combination of the properties passed in and
 *  parameters from the file.
 *  <p>
 *  <i>Note for the future: This assumes that the TaggerConfig in the file
 *  has already been read and used.  It might be better to refactor
 *  things so that is all done inside this method, but for the moment
 *  it seemed better to leave working code alone [cdm 2008].</i>
 *
 *  @param config The tagger config
 *  @param rf DataInputStream to read from.  It's the caller's job to open and close this stream.
 *  @param printLoading Whether to print a message saying what model file is being loaded and how long it took when finished.
 *  @throws IOException If I/O errors
 *  @throws ClassNotFoundException If serialization errors
 */
protected void readModelAndInit(Properties config, DataInputStream rf) {
    try {
      TaggerConfig taggerConfig = TaggerConfig.readConfig(rf);
      if (config != null) {
        taggerConfig.setProperties(config);
      }
      // then init tagger
      init(taggerConfig);

      xSize = rf.readInt();
      ySize = rf.readInt();
      // dict = new Dictionary();  // this method is called in constructor, and it's initialized as empty already
      dict.read(rf);

      tags.read(rf);
      readExtractors(rf);
      dict.setAmbClasses(ambClasses, veryCommonWordThresh, tags);

      int[] numFA = new int[extractors.size() + extractorsRare.size()];
      int sizeAssoc = rf.readInt();
      fAssociations = Helpers.newArrayList();
      for (int i = 0; i < extractors.size() + extractorsRare.size(); ++i) {
        fAssociations.add(new HashMap<String, int[]>());
      }

      for (int i = 0; i < sizeAssoc; i++) {
        int numF = rf.readInt();
        FeatureKey fK = new FeatureKey();
        fK.read(rf);
        numFA[fK.num]++;

        // TODO: rewrite the writing / reading code to store
        // fAssociations in a cleaner manner?  Only do this when
        // rebuilding all the tagger models anyway.  When we do that, we
        // can get rid of FeatureKey
        Map<String, int[]> fValueAssociations = fAssociations.get(fK.num);
        int[] fTagAssociations = fValueAssociations.get(fK.val);
        if (fTagAssociations == null) {
          fTagAssociations = new int[ySize];
          for (int j = 0; j < ySize; ++j) {
            fTagAssociations[j] = -1;
          }
          fValueAssociations.put(fK.val, fTagAssociations);
        }
        fTagAssociations[tags.getIndex(fK.tag)] = numF;
      }
      prob = new LambdaSolveTagger(rf);
    } catch (IOException e) {
      throw new RuntimeException("Unrecoverable error while loading a tagger model", e);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
      throw new RuntimeException("Unrecoverable error while loading a tagger model", e);
    }
  }
}