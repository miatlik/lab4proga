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
class Player {
    private List<Card> hand; // Список для хранения карт

    public Player() {
        hand = new ArrayList<>();
    }
    // Получение карты в руку игрока
    public void ruka(int cardValue) {
        if (hand.size() < Deck.getMaxCards()) {
            hand.add(new Card(cardValue)); // Динамическое выделение карты
        }
    }
    // Вывод руки игрока
    public void myvivod() {
        int sum = 0;
        System.out.print("\nМои карты: ");
        for (Card card : hand) {
            System.out.print(card.getValue() + ", ");
            sum += card.getValue();
        }
        System.out.println("Сумма: " + sum + "/21");
    }
    // Вывод руки противника без первой карты
    public void opvivodhide() {
        int sum = 0;
        System.out.print("\nКарты противника: ?, ");
        for (int i = 1; i < hand.size(); i++) {
            System.out.print(hand.get(i).getValue() + ", ");
            sum += hand.get(i).getValue();
        }
        System.out.println("Сумма: ? + " + sum + "/21");
    }
    // Вывод полной руки противника
    public void opvivodopen() {
        int sum = 0;
        System.out.print("\nКарты противника: ");
        for (Card card : hand) {
            System.out.print(card.getValue() + ", ");
            sum += card.getValue();
        }
        System.out.println("Сумма: " + sum + "/21");
    }
    // Сумма очков
    public int getTotalValue() {
        int sum = 0;
        for (Card card : hand) {
            sum += card.getValue();
        }
        return sum;
    }
    // Вывод результата игры
    public void vivodrez(Player opponent) {
        int playerScore = this.getTotalValue();
        int opponentScore = opponent.getTotalValue();
        if (playerScore > 21 && opponentScore < 22) {
            System.out.println("\nУ вас перебор. Вы проиграли\n");
        } else if (opponentScore > 21 && playerScore < 22) {
            System.out.println("\nУ противника перебор. Вы выиграли\n");
        } else if (playerScore > 21 && opponentScore > 21) {
            if (playerScore > opponentScore) {
                System.out.println("\nУ вас и противника перебор. Вы проиграли, так как имеете больше очков\n");
            } else {
                System.out.println("\nУ вас и противника перебор. Вы выиграли, так как имеете меньше очков\n");
            }
        } else if (playerScore < 22 && opponentScore < 22) {
            if (playerScore > opponentScore) {
                System.out.println("\nВы выиграли. Вы ближе к 21 очку\n");
            } else if (playerScore < opponentScore) {
                System.out.println("\nВы проиграли. Противник ближе к 21 очку\n");
            } else {
                System.out.println("\nНичья\n");
            }
        }
    }
    // Простейший искусственный интеллект для противника, который берет карту, если у него меньше 17 очков
    public boolean reshenie_ai() {
        return this.getTotalValue() < 17; // Возвращаем true (взять карту) или false (остановиться)
    }
}