function setSpeed(vehID, speed)
%setSpeed Sets the speed in m/s for the named vehicle within the last step.
%   setSpeed(VEHID,SPEED) Sets the speed in m/s for the named vehicle 
%   within the last step.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: setSpeed.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
traci.sendDoubleCmd(constants.CMD_SET_VEHICLE_VARIABLE, constants.VAR_SPEED, vehID, speed);