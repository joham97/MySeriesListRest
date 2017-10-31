# MySeriesListRest
A REST Service for MySeriesList with the Spring Framework.

## Endpoints

Method | URL
----------|------------------------
**GET**|`/search`
**GET**|`/series?keywords=<<keyword>>`
**GET**|`/series/cover?keywords=<<keyword>>&thumbnail=[true|false]`
**GET**|`/series/seasons?keywords=<<keyword>>`
**GET**|`/episode/cover?keywords=<<keyword>>&thumbnail=[true|false]`

## TODO
- Add TheTVDBAPI to Maven Central when finished.
