function setWidth(vehID, width)
%setWidth Sets the width in m for this vehicle.
%   setWidth(VEHID,WIDTH) Sets the width in m for this vehicle.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$

import traci.constants
traci.sendDoubleCmd(constants.CMD_SET_VEHICLE_VARIABLE, constants.VAR_WIDTH, vehID, width);