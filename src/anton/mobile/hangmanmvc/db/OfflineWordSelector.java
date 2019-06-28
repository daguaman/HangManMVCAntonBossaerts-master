package anton.mobile.hangmanmvc.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OfflineWordSelector implements IDBConnector{
	private List<String> words;

	@Override
	public String getWord(String category) {
		words = new ArrayList<String>();
		if(category.equals("fruit")){
			words.add("apple");
			words.add("banana");
			words.add("kiwi");
			words.add("pineapple");
			words.add("pear");
			words.add("strawberry");
		}
		if(category.equals("animal")){
			words.add("zebra");
			words.add("elephant");
			words.add("bird");
			words.add("shark");
			words.add("horse");
			words.add("giraffe");			
		}
		Random r = new Random();
		int i = r.nextInt(words.size());
		return words.get(i);
	}
}
