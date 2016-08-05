/****************************************************************************/
/// @file    GUIIconSubSys.h
/// @author  Daniel Krajzewicz
/// @date    Jul 2003
/// @version $Id: GUIIconSubSys.h 21107 2016-07-04 12:57:59Z palcraft $
///
// A class to manage icons of SUMO
/****************************************************************************/
// SUMO, Simulation of Urban MObility; see http://sumo.dlr.de/
// Copyright (C) 2001-2016 DLR (http://www.dlr.de/) and contributors
/****************************************************************************/
//
//   This file is part of SUMO.
//   SUMO is free software: you can redistribute it and/or modify
//   it under the terms of the GNU General Public License as published by
//   the Free Software Foundation, either version 3 of the License, or
//   (at your option) any later version.
//
/****************************************************************************/
#ifndef GUIIconSubSys_h
#define GUIIconSubSys_h


// ===========================================================================
// included modules
// ===========================================================================
#ifdef _MSC_VER
#include <windows_config.h>
#else
#include <config.h>
#endif

#include <fx.h>
#include "GUIIcons.h"


// ===========================================================================
// class definitions
// ===========================================================================
class GUIIconSubSys {
public:
    /// @brief Initiate GUIIconSubSys
    /// @param[in] a FOX Toolkit APP
    static void init(FXApp* a);

    /// @brief returns a icon previously defined in the enum GUIIcon
    /// @param[in] a FOX Toolkit APP
    static FXIcon* getIcon(GUIIcon which);

    /// @brief close GUIIconSubSys
    static void close();

private:
    /// @brief constructor
    /// @note is private because is called by the static function init(FXApp* a)
    GUIIconSubSys(FXApp* a);

    /// @brief destructor
    ~GUIIconSubSys();

    /// @brief instance of GUIIconSubSys
    static GUIIconSubSys* myInstance;

    /// @brief vector with the icons
    FXIcon* myIcons[ICON_MAX];
};


#endif

/****************************************************************************/

