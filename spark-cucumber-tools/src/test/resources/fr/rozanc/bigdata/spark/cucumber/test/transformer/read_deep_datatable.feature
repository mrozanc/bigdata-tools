Feature: Read many levels inside a DataTable

  Scenario: Read simple DataTable
    Given I read this DataTable:
      | id | name  | quantity | amount | date       | innerData.nestedField |
      | 5  | hello | 15       | 45.12  | 2019-06-18 | example               |
      | 7  | world | -4       | 3.14   | 2019-06-01 | I'm nested            |

#    And I read this DataTable:
#      |  |