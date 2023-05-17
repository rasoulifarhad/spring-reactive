package com.farhad.example.webfluxdemo;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
public class DemoResource {
    private String[] speech = {
            "i", "have", "begun", "this", "letter", "five", "times", "and", "torn", "it", "up", "five", "times", "i",
            "keep", "seeing",
            "your", "face", "which", "is", "also", "the", "face", "of", "your", "father", "and", "my", "brother", "i",
            "have", "known",
            "both", "of", "you", "all", "your", "lives", "and", "have", "carried", "your", "daddy", "in", "my", "arms",
            "and", "on", "my",
            "shoulders", "kissed", "him", "and", "spanked", "him", "and", "watched", "him", "learn", "to", "walk", "i",
            "t", "know", "if",
            "you", "have", "known", "anybody", "from", "that", "far", "back", "if", "you", "have", "loved", "anybody",
            "that", "long",
            "first", "as", "an", "infant", "then", "as", "a", "child", "then", "as", "a", "man", "you", "gain", "a",
            "strange", "perspective",
            "on", "time", "and", "human", "pain", "and", "effort", "other", "people", "cannot", "see", "what", "i",
            "see", "whenever", "i",
            "look", "into", "your", "father's", "face", "for", "behind", "your", "father's", "face", "as", "it", "is",
            "today", "are", "all",
            "those", "other", "faces", "which", "were", "his", "let", "him", "laugh", "and", "i", "see", "a", "cellar",
            "your", "father", "does",
            "not", "remember", "and", "a", "house", "he", "does", "not", "remember", "and", "i", "hear", "in", "his",
            "present", "laughter",
            "his", "laughter", "as", "a", "child", "let", "him", "curse", "and", "i", "remember", "his", "falling",
            "down", "the", "cellar",
            "steps", "and", "howling", "and", "i", "remember", "with", "pain", "his", "tears", "which", "my", "hand",
            "or", "your", "grandmother's",
            "hand", "so", "easily", "wiped", "away", "but", "no", "one's", "hand", "can", "wipe", "away", "those",
            "tears", "he", "sheds", "invisibly",
            "today", "which", "one", "hears", "in", "his", "laughter", "and", "in", "his", "speech", "and", "in", "his",
            "songs", "i", "know", "what",
            "the", "world", "has", "done", "to", "my", "brother", "and", "how", "narrowly", "he", "has", "survived",
            "it", "and", "i", "know", "which",
            "is", "much", "worse", "and", "this", "is", "the", "crime", "of", "which", "i", "accuse", "my", "country",
            "and", "my", "countrymen", "and",
            "for", "which", "neither", "i", "nor", "time", "nor", "history", "will", "ever", "forgive", "them", "that",
            "they", "have", "destroyed",
            "and", "are", "destroying", "hundreds", "of", "thousands", "of", "lives", "and", "do", "not", "know", "it",
            "and", "do", "not", "want",
            "to", "know", "it", "one", "can", "be", "indeed", "one", "must", "strive", "to", "become", "tough", "and",
            "philosophical", "concerning",
            "destruction", "and", "death", "for", "this", "is", "what", "most", "of", "mankind", "has", "been", "best",
            "at", "since", "we", "have",
            "heard", "of", "war", "remember", "i", "said", "most", "of", "mankind", "but", "it", "is", "not",
            "permissible", "that", "the", "authors",
            "of", "devastation", "should", "also", "be", "innocent", "it", "is", "the", "innocence", "which",
            "constitutes", "the", "crime", "now", "my",
            "dear", "namesake", "these", "innocent", "and", "well", "meaning", "people", "your", "countrymen", "have",
            "caused", "you", "to", "be", "born",
            "under", "conditions", "not", "far", "removed", "from", "those", "described", "for", "us", "by", "charles",
            "dickens", "in", "the", "london", "of",
            "more", "than", "a", "hundred", "years", "ago", "i", "hear", "the", "chorus", "of", "the", "innocents",
            "screaming", "no", "this", "is", "not", "true", "how",
            "bitter", "you", "are", "but", "i", "am", "writing", "this", "letter", "to", "you", "to", "try", "to",
            "tell", "you", "something", "about", "how", "to", "handle",
            "them", "for", "most", "of", "them", "do", "not", "yet", "really", "know", "that", "you", "exist", "i",
            "know", "the", "conditions", "under", "which",
            "you", "were", "born", "for", "i", "was", "there", "your", "countrymen", "were", "not", "there", "and",
            "haven't", "made", "it", "yet", "your",
            "grandmother", "was", "also", "there", "and", "no", "one", "has", "ever", "accused", "her", "of", "being",
            "bitter", "i", "suggest", "that", "the", "innocent", "check", "with", "her", "she", "isn't", "hard", "to",
            "find", "your", "countrymen", "don't", "know", "that", "she", "exists", "either", "though", "she", "has",
            "been", "working", "for", "them", "all", "their", "lives", "well", "you", "were", "born", "here", "you",
            "came", "something", "like", "fifteen", "years", "ago", "and", "though", "your", "father", "and", "mother",
            "and", "grandmother", "looking", "about", "the", "streets", "through", "which", "they", "were", "carrying",
            "you", "staring", "at", "the", "walls", "into", "which", "they", "brought", "you", "had", "every", "reason",
            "to", "be", "heavy", "hearted", "yet", "they", "were", "not", "for", "here", "you", "were", "big", "james",
            "named", "for", "me", "you", "were", "a", "big", "baby", "i", "was", "not", "here", "you", "were", "to",
            "be", "loved", "to", "be", "loved", "baby", "hard", "at", "once", "and", "forever", "to", "strengthen",
            "you", "against", "the", "loveless", "world", "remember", "that", "i", "know", "how", "black", "it",
            "looks", "today", "for", "you", "it", "looked", "black", "that", "day", "too", "yes", "we", "were",
            "trembling", "we", "have", "not", "stopped", "trembling", "yet", "but", "if", "we", "had", "not", "loved",
            "each", "other", "none", "of", "us", "would", "have", "survived", "and", "now", "you", "must", "survive",
            "because", "we", "love", "you", "and", "for", "the", "sake", "of", "your", "children", "and", "your",
            "children's", "children", "this", "innocent", "country", "set", "you", "down", "in", "a", "ghetto", "in",
            "which", "in", "fact", "it", "intended", "that", "you", "should", "perish", "let", "me", "spell", "out",
            "precisely", "what", "i", "mean", "by", "that", "for", "the", "heart", "of", "the", "matter", "is", "here",
            "and", "the", "crux", "of", "my", "dispute", "with", "my", "country", "you", "were", "born", "where", "you",
            "were", "born", "and", "faced", "the", "future", "that", "you", "faced", "because", "you", "were", "black",
            "and", "for", "no", "other", "reason", "the", "limits", "to", "your", "ambition", "were", "thus",
            "expected", "to", "be", "settled", "you", "were", "born", "into", "a", "society", "which", "spelled", "out",
            "with", "brutal", "clarity", "and", "in", "as", "many", "ways", "as", "possible", "that", "you", "were",
            "a", "worthless", "human", "being", "you", "were", "not", "expected", "to", "aspire", "to", "excellence",
            "you", "were", "expected", "to", "make", "peace", "with", "mediocrity", "wherever", "you", "have", "turned",
            "james", "in", "your", "short", "time", "on", "this", "earth", "you", "have", "been", "told", "where",
            "you", "could", "go", "and", "what", "you", "could", "do", "and", "how", "you", "could", "do", "it",
            "where", "you", "could", "live", "and", "whom", "you", "could", "marry"
    };

    @GetMapping(value = "/speech", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<List<String>> getSpeech() {
        return Flux
                .fromArray(speech)
                .map(word -> {
                    Treatment treatment = Treatment.randomTrreatment();
                    String treatedWord = word;
                    switch (treatment) {
                        case   UPPER :
                            treatedWord = word.toUpperCase();
                            break;
                        case   LOWER :
                            treatedWord = word.toLowerCase();
                            break;
                    
                        default:
                            break;
                    }
                    return Arrays.asList(treatedWord, treatment.toString());
                })
                .delayElements(Duration.ofSeconds(1))
                .repeat()
                .log();
    }
}
