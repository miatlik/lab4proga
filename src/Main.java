import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

class Card {
    private int value; // Значение карты

    public Card(int val) {
        this.value = val;
    }

    public int getValue() {
        return value;
    }
}
class Deck {
    private static final int MAX_CARDS = 11; // Максимальное количество карт
    private int[] cards; // Массив для хранения значений карт
    private int size; // Текущий размер колоды
    private Random random;

    public Deck() {
        cards = new int[MAX_CARDS];
        random = new Random();
        vvodkolodi();
    }

    // Инициализация игральной колоды с очками от 1 до 11
    public void vvodkolodi() {
        for (int i = 0; i < MAX_CARDS; i++) {
            cards[i] = i + 1;
        }
        size = MAX_CARDS; // Инициализация размера колоды
    }
    // Случайный выбор карты из колоды
    public int viborkarti() {
        if (size <= 0) return -1; // Возвращаем невалидное значение
        int index = random.nextInt(size);
        int cardValue = cards[index];
        // Сдвиг оставшихся карт
        System.arraycopy(cards, index + 1, cards, index, size - index - 1);
        size--;
        return cardValue; // Возвращаем значение карты
    }
    // Размер колоды
    public int getSize() {
        return size;
    }
    // Возвращение максимального количества карт
    public static int getMaxCards() {
        return MAX_CARDS;
    }
}