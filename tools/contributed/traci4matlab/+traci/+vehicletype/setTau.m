function setTau(typeID, tau)
%setTau Sets the driver's reaction time for vehicles of this type.
%   setTau(TYPEID,TAU) Sets the driver's reaction time in s for vehicles of
%   this type.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: setTau.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
traci.sendDoubleCmd(constants.CMD_SET_VEHICLETYPE_VARIABLE, constants.VAR_TAU, typeID, tau);