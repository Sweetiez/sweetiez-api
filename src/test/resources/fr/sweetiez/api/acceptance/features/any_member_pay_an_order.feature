Feature: Any customer - Pay an order

  A customer want to pay an order.

  Background:
    Given existing customers:
      | id  | firstName | lastName | email                      |
      | abc | Mickael   | JACKSON  | thriller@thriller.thriller |
      | def | John      | SNOW     | winter.is@coming.got       |
      | ghi | Chuck     | NOLAND   | i.am@sorry.wilson          |


  Scenario Outline: Pay an order with a credit card successfully
    Given I am authenticated as "<customer_email>"
    And my loyalty points are "<points_before>"
    And the credit card with the number "<credit_card_number>" hold by "<credit_card_holder>" expiring on "<credit_card_expiration_date>" with the CCV "<credit_card_ccv>" is valid
    When I try to pay the cart containing "<cart_items_number>" items for a value of "<cart_price>" euros to receive my order the "<delivery_date>"
    Then the purchase is a success confirmed by a new order created
    And my loyalty points are "<points_after>"
    Examples:
      | customer_email             | cart_items_number | cart_price | points_before | points_after| delivery_date       | credit_card_number  | credit_card_holder | credit_card_expiration_date | credit_card_ccv |
      | thriller@thriller.thriller | 2                 | 50         | 172           | 222         | 2022-01-01T14:57:00 | 0000 0000 0000 0000 | JACKSON MICKAEL    | 01/00                       | 000             |
      | winter.is@coming.got       | 7                 | 35.95      | 0             | 35          | 2022-05-08T14:57:00 | 1234 5678 9087 6543 | SNOW JOHN          | 07/34                       | 123             |
      | i.am@sorry.wilson          | 6                 | 122.70     | 4045          | 4167        | 2022-12-31T14:57:00 | 9999 9999 9999 9999 | NOLAND CHUCK       | 12/99                       | 999             |

#    Given existing sweets:
#      | id  | name              | category  | price |
#      | abc | Chocolate fondant | sweet     | 2.30  |
#      | def | Burrito           | salty     | 3.20  |
#      | ghi | Mini burger       | sweet     | 1.95  |


#
#  Scenario Outline: Pay an order with a credit card unsuccessfully
#    Given I am authenticated as "<customer_email>"
#    And the cart value is "<cart_price>" euros containing "<cart_articles_number>" items
#    And my loyalty points are "<points_before>"
#    And the credit card with the number "<credit_card_number>" hold by "<credit_card_holder>" expiring on "<credit_card_expiration_date>" with the CCV "<credit_card_ccv>" is not valid
#    When I try to pay the order containing "<cart_articles_number_before>" articles for a value of "<cart_price_before>" euros
#    Then the purchase is a failure
#    And the cart value is "<cart_price_after>" euros with "<cart_articles_number_after>" items remaining
#    And my loyalty points are "<points_after>"
#    Examples:
#      | customer_email             | cart_articles_number | cart_price | points_before | points_after | credit_card_number  | credit_card_holder | credit_card_expiration_date | credit_card_ccv |
#      | thriller@thriller.thriller | 60                   | 50         | 172           | 172          | 0000 0000 0000 0000 | JACKSON MICKAEL    | 00/00                       | 000             |
#      | winter.is@coming.got       | 30                   | 35         | 0             | 0            | 1234 5678 9087 6543 | SNOW JOHN          | b7/$Y                       | 123             |
#      | i.am@sorry.wilson          | 120                  | 122        | 4045          | 4045         | 9999 9999 9999 9999 | NOLAND CHUCK       | 13/100                      | 999             |
#
#
#  Scenario Outline: Pay an order with PayPal successfully
#    Given I am authenticated as "<customer_email>"
#    And the cart value is "<cart_price>" euros containing "<cart_articles_number>" items
#    And my loyalty points are "<points_before>"
#    And my PayPal details are correct
#    When I try to pay the order containing "<cart_articles_number_before>" articles for a value of "<cart_price_before>" euros
#    Then the purchase is a success
#    And the cart value is "<cart_price_after>" euros with "<cart_articles_number_after>" items remaining
#    And my loyalty points are "<points_after>"
#    Examples:
#      | customer_email             | cart_articles_number | cart_price | points_before | points_after |
#      | thriller@thriller.thriller | 60                   | 50         | 172           | 272          |
#      | winter@is.coming           | 30                   | 35         | 0             | 50           |
#      | i.am@sorry.wilson          | 120                  | 122        | 4045          | 4245         |
#
#  Scenario Outline: Pay an order with PayPal unsuccessfully
#    Given I am authenticated as "<customer_email>"
#    And the cart value is "<cart_price>" euros containing "<cart_articles_number>" items
#    And my loyalty points are "<points_before>"
#    And my PayPal details are incorrect
#    When I try to pay the order containing "<cart_articles_number_before>" articles for a value of "<cart_price_before>" euros
#    Then the purchase is a failure
#    And the cart value is "<cart_price_after>" euros with "<cart_articles_number_after>" items remaining
#    And my loyalty points are "<points_after>"
#    Examples:
#      | customer_email             | cart_articles_number | cart_price | points_before | points_after |
#      | thriller@thriller.thriller | 60                   | 50         | 172           | 172          |
#      | winter@is.coming           | 30                   | 35         | 0             | 0            |
#      | i.am@sorry.wilson          | 120                  | 122        | 4045          | 4045         |
