"""
@file    roundabout.py
@author  Daniel Krajzewicz
@author  Laura Bieker
@author  Karol Stosiek
@author  Michael Behrisch
@date    2011-11-28
@version $Id: roundabout.py 18096 2015-03-17 09:50:59Z behrisch $

This file contains a Python-representation of a single roundabout.

SUMO, Simulation of Urban MObility; see http://sumo.dlr.de/
Copyright (C) 2008-2015 DLR (http://www.dlr.de/) and contributors

This file is part of SUMO.
SUMO is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 3 of the License, or
(at your option) any later version.
"""


class Roundabout:

    def __init__(self, nodes):
        self._nodes = nodes

    def getNodes(self):
        return self._nodes
