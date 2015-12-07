function setWidth(typeID, width)
%setWidth Sets the width in m for vehicles of this type.
%   setWidth(TYPEID,WIDTH) Sets the width in m of vehicles of this type.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$

import traci.constants
traci.sendDoubleCmd(constants.CMD_SET_VEHICLETYPE_VARIABLE, constants.VAR_WIDTH, typeID, width);