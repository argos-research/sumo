function setLength(vehID, length)
%setLength Sets the length in m for the given vehicle.
%   setLength(VEHID,LENGTH) Sets the length in m for the given vehicle.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$

import traci.constants
traci.sendDoubleCmd(constants.CMD_SET_VEHICLE_VARIABLE, constants.VAR_LENGTH, vehID, length);