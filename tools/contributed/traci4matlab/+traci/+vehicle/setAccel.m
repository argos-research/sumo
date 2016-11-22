function setAccel(vehID, accel)
%setAccel Set the acceleration of the vehicle.
%   setAccel(VEHID,ACCEL) Sets the maximum acceleration in m/s^2 for this 
%   vehicle.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: setAccel.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
traci.sendDoubleCmd(constants.CMD_SET_VEHICLE_VARIABLE, constants.VAR_ACCEL, vehID, accel);