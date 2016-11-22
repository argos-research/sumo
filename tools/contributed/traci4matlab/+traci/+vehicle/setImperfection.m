function setImperfection(vehID, imperfection)
%setImperfection Sets the driver's imperfection (dawdling).
%   setImperfection(VEHID,IMPERFECTION) Sets the driver's imperfection
%   (dawdling). IMPERFECTION is a double precision integer ranging from
%   zero to one.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: setImperfection.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
traci.sendDoubleCmd(constants.CMD_SET_VEHICLE_VARIABLE, constants.VAR_IMPERFECTION, vehID, imperfection);