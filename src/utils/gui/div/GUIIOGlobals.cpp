/****************************************************************************/
/// @file    GUIIOGlobals.cpp
/// @author  Daniel Krajzewicz
/// @author  Michael Behrisch
/// @date    2004-11-23
/// @version $Id: GUIIOGlobals.cpp 22608 2017-01-17 06:28:54Z behrisch $
///
// The folder used as last
/****************************************************************************/
// SUMO, Simulation of Urban MObility; see http://sumo.dlr.de/
// Copyright (C) 2001-2017 DLR (http://www.dlr.de/) and contributors
/****************************************************************************/
//
//   This file is part of SUMO.
//   SUMO is free software: you can redistribute it and/or modify
//   it under the terms of the GNU General Public License as published by
//   the Free Software Foundation, either version 3 of the License, or
//   (at your option) any later version.
//
/****************************************************************************/


// ===========================================================================
// included modules
// ===========================================================================
#ifdef _MSC_VER
#include <windows_config.h>
#else
#include <config.h>
#endif

#include "GUIIOGlobals.h"

#ifdef CHECK_MEMORY_LEAKS
#include <foreign/nvwa/debug_new.h>
#endif // CHECK_MEMORY_LEAKS


// ===========================================================================
// global variable definitions
// ===========================================================================
FXString gCurrentFolder;



/****************************************************************************/

