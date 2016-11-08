function setEmissionClass(typeID, clazz)
%setEmissionClass ets the emission class of vehicles for this type.
%   setEmissionClass(TYPEID,CLASS) Sets the emission class of vehicles of 
%   this type.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: setEmissionClass.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
traci.sendStringCmd(constants.CMD_SET_VEHICLETYPE_VARIABLE, constants.VAR_EMISSIONCLASS, typeID, clazz);