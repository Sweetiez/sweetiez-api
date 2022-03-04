Feature: Any customer - Pay an order

  A customer want to pay an order.

  Background:
    Given existing customers:
      | id  | firstName | lastName | email                      |
      | abc | Mickael   | JACKSON  | thriller@thriller.thriller |
      | def | John      | SNOW     | winter@is.coming           |
      | ghi | Chuck     | NOLAND   | i.am@sorry.wilson          |

    Given existing sweets:
      | id  | name              | category  | price |
      | abc | Chocolate fondant | sweet     | 2.30  |
      | def | Burrito           | salty     | 3.20  |
      | ghi | Mini burger       | sweet     | 1.95  |

  Scenario Outline: Pay an order with a credit card successfully
    Given I am authenticated as "<customer_email>"
    And the cart value is "<cart_price_before>" with "<cart_articles_number_before>" items remaining
    And my points of loyalty are "<points_before>"
    And my credit card details are correct
    When I try to pay the cart value
    Then the purchase is a success
    And the cart value is "<cart_price_after>" with "<cart_articles_number_after>" items remaining
    And my loyalty points are "<points_after>"
    Examples:
      | customer_email             | cart_articles_number_before | cart_articles_number_after | cart_price_before | cart_price_after | points_before | points_after |
      | thriller@thriller.thriller | 60                          | 0                          | 50                | 0                | 172           | 272          |
      | winter@is.coming           | 30                          | 0                          | 35                | 0                | 0             | 50           |
      | i.am@sorry.wilson          | 120                         | 0                          | 122               | 0                | 4045          | 4245         |
    
