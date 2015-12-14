# Name

Burp Event Stream


## Version

0.0.2


## Author

Adam Piper <adam@ahri.net>


## Description

An extension that passes along Issues discovered by Burp to either stdout or an ElasticSearch database.


## Configuration

When running Burp via the UI, configuration is _always_ sourced from the Java Preferences store. Conversely, when running
headless the configuration is _always_ sourced from environment variables.

The environment variables are guessable from UI, i.e.

    - "Type"                 -> BURP_EVENTSTREAM_TYPE
    - "Elasticsearch Host"   -> BURP_EVENTSTREAM_ELASTICSEARCH_HOST
    - "Elasticsearch Port"   -> BURP_EVENTSTREAM_ELASTICSEARCH_PORT
    - "Elasticsearch Prefix" -> BURP_EVENTSTREAM_ELASTICSEARCH_PREFIX


#### Notes

Scan IDs are generated in the extension, and the end of a scan is detected via a cooldown period calculated from current
user settings. Concurrent scan IDs are therefore not supported and the extension will consider concurrent scans to be
part of a single, overall scan. In the future, if Burp attaches its own IDs to scans and announces the start/end of
scans, this could be more accurate (and timely!)

Currently, authenticated access to ElasticSearch is not supported.

It's possible to switch output type while a scan is in progress. No special handling occurs; events after this point
will be streamed to the latter output, i.e. it will be missing the start of the current scan.
