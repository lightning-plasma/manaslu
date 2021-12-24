package com.archetype.manaslu.entity

import java.util.Timer

class NameResolverRefreshTimer(
    name: String,
    isDaemon: Boolean
) : Timer(name, isDaemon)
