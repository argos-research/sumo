function setMaxSpeed(vehID, speed)
%setMaxSpeed Sets the maximum speed in m/s for this vehicle.
%   setMaxSpeed(VEHID,SPEED) Sets the maximum speed in m/s for this vehicle.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: setMaxSpeed.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
traci.sendDoubleCmd(constants.CMD_SET_VEHICLE_VARIABLE, constants.VAR_MAXSPEED, vehID, speed);